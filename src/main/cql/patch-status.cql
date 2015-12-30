use patch;

create table patch_status (
  member_id        uuid,
  resource_id      uuid,
  status_summary   text,
  last_current     timestamp,
  last_assessed    timestamp,
  installed_patches set<uuid>,
  missing_patches   set<uuid>,

  PRIMARY KEY (member_id, resource_id)
  );
