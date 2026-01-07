 create sequence employee_sequence
 start with 1
 increment by 1;

create table lab6_parent
(ee# integer not null,
fname varchar2(30),
lname varchar2 (30),
salary         number
);

create table lab6_child
(ee# integer not null,
fname varchar2(30),
lname varchar2(3),
age integer
);

alter table lab6_PARENT add constraint lab6_parent_ename_uq unique (fname);

-- populate the Parent table with data
Declare
    rows_inserted integer := 0;
Begin
    Loop
        Begin
            INSERT INTO lab6_parent(EE#, fname, lname, salary)
            VALUES(employee_sequence.nextval, dbms_random.string('U', 6),'King'||rows_inserted,1000);
            --Only increment counter when no duplicate exception
            rows_inserted := rows_inserted + 1;
        Exception When DUP_VAL_ON_INDEX Then Null;
        End;
        exit when rows_inserted = 1000;
        commit;
    End loop;
    --commit;
End;
/

alter table lab6_child add constraint lab6_child_fname_ee#_uq unique (fname,ee#);

-- populate the child table with data
Declare
    rows_inserted integer := 0;
Begin
    Loop
        Begin
            INSERT INTO lab6_child(EE#, fname, lname, age)
            VALUES(dbms_random.value(1,1000), dbms_random.string('U', 4),dbms_random.string('U', 3),15);
            --Only increment counter when no duplicate exception
            rows_inserted := rows_inserted + 1;
        Exception When DUP_VAL_ON_INDEX Then Null;
        End;
        exit when rows_inserted = 10000;
        commit;
    End loop;
    --commit;
End;
/
