use patch;

create table patch_definition (
  patch_id     uuid primary key,
  product      text,
  description  text,
  update_type  text,
  test_status  text,
  restart      boolean);
