package net.continuum.patch.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


import net.continuum.patch.model.PatchDefinition;
import net.continuum.patch.dao.PatchDefinitionDao;

@RestController
public class PatchDefinitionController
{
    private static Cluster cluster = Cluster.builder()
        .addContactPoint("127.0.0.1")
        .build();
    private static Session session = cluster.connect("Patch");
    
    private static PatchDefinitionDao patchDefinitionDao = new PatchDefinitionDao(session);
    
    @RequestMapping(value="/patchDefinition/{uuid}", method=RequestMethod.GET)
    public ResponseEntity<PatchDefinition> readPatchDefinition(@PathVariable String uuid)
    {
        Optional<PatchDefinition> patchDefn =  patchDefinitionDao.read(uuid);

        return patchDefn.flatMap(pd -> Optional.of(new ResponseEntity(pd, HttpStatus.OK)))
            .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value="/patchDefinition/{uuid}", method=RequestMethod.PUT)
    public ResponseEntity<PatchDefinition> updatePatchDefinition(@PathVariable("uuid") String patchId,
                                                                 @RequestBody PatchDefinition updatedPatchDefinition)
    {
        Optional<PatchDefinition> patchDefn = patchDefinitionDao.read(patchId);

        if (patchDefn.isPresent()) {
            PatchDefinition patch = patchDefn.get();
            patch.setProduct(updatedPatchDefinition.getProduct());
            patch.setDescription(updatedPatchDefinition.getDescription());
            patch.setUpdateType(updatedPatchDefinition.getUpdateType());
            patch.setTestStatus(updatedPatchDefinition.getTestStatus());
            patch.setRestartRequired(updatedPatchDefinition.getRestartRequired());

            patchDefinitionDao.save(patchId, patch);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<PatchDefinition>(HttpStatus.NOT_FOUND);
        }           
    }
    
    @RequestMapping(value="/patchDefinition", method=RequestMethod.POST)
    public ResponseEntity<Void> createPatchDefinition(@RequestBody PatchDefinition patchDefinition,
                                                      UriComponentsBuilder ucBuilder)
    {
        UUID patchId = patchDefinitionDao.create(patchDefinition);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/patchDefinition/{uuid}").buildAndExpand(patchId).toUri());
        
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
}
