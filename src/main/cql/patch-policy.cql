use patch;

create type third_party_patch_policy (
  vendor  text,
  product text,
  os      text,
  minor   boolean,
  major   boolean
  );

create type timeofday (
  hour     int,
  minute   int
  );

create type window (
  weekdays        set<int>,
  weeksofmonth    set<int>,
  monthdays       set<int>,
  window_start    frozen<timeofday>,
  window_end      frozen<timeofday>
  );
  
create table patch_policy (
  member_id uuid,
  name      text,
  type      text,
  sites     set<uuid>,
  servers   set<uuid>,

  download_window  frozen<window>,
  install_window   frozen<window>,
  
  thirdparty_patches  set<frozen <third_party_patch_policy>>,
  
  PRIMARY KEY (member_id, name)
  );

