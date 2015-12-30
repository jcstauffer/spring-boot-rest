package net.continuum.patch.dao;

import java.util.Optional;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import net.continuum.patch.model.PatchDefinition;

public class PatchDefinitionDao 
{
    private Session session;
    
    public PatchDefinitionDao(Session session)
    {
        this.session = session;
        System.err.println("UUID: [" + UUID.randomUUID() + "]");
    }

    public Optional<PatchDefinition> read(String uuid)
    {
        PreparedStatement stmt =
            session.prepare
            ("SELECT product, description, update_type, " +
             "       test_status, restart " +
             "FROM patch_definition " +
             "WHERE patch_id = ?");

        BoundStatement boundStmt = new BoundStatement(stmt);

        ResultSet rs = session.execute(boundStmt.bind(UUID.fromString(uuid)));

        if (rs.isExhausted()) {
            return Optional.empty();
        } else {
            Row row = rs.one();

            return Optional.of(new PatchDefinition(uuid, row.getString("product"),
                                                   row.getString("description"),
                                                   row.getString("update_type"),
                                                   row.getString("test_status"),
                                                   row.getBool("restart")));
        }
    }

    public UUID create(PatchDefinition patchDefn)
    {
        BoundStatement stmt = new BoundStatement(session.prepare
           ("Insert into patch_definition (patch_id, product, description, update_type, " +
             "                              test_status, restart) " +
             "Values (?, ?, ?, ?, ?, ?)"));
        
        UUID patchId = UUID.randomUUID();

        session.execute(stmt.bind(patchId, patchDefn.getProduct(), patchDefn.getDescription(),
                                  patchDefn.getUpdateType(), patchDefn.getTestStatus(),
                                  patchDefn.getRestartRequired()));

        return patchId;
    }

    public void save(String patchId, PatchDefinition patchDefn)
    {
        BoundStatement stmt = new BoundStatement(session.prepare
          ("update patch_definition set product = ?, description = ?,  update_type = ?, " +
           "test_status = ?, restart = ? where patch_id = ?"));

        session.execute(stmt.bind(patchDefn.getProduct(), patchDefn.getDescription(),
                                  patchDefn.getUpdateType(), patchDefn.getTestStatus(),
                                  patchDefn.getRestartRequired(),
                                  UUID.fromString(patchId)));
    }
}
