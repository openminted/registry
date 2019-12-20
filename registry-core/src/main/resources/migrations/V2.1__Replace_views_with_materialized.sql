CREATE OR REPLACE FUNCTION ReplaceViewWithMaterialized()
  RETURNS VOID
AS
$$
DECLARE t_row TEXT;
DECLARE temp_definition TEXT;
BEGIN
    FOR t_row IN select table_name from INFORMATION_SCHEMA.views WHERE table_schema = ANY (current_schemas(false)) LOOP
        select definition from pg_views where viewname = t_row into temp_definition;
        EXECUTE 'DROP VIEW IF EXISTS ' || t_row;
        EXECUTE 'CREATE MATERIALIZED VIEW ' || t_row || ' AS ' || temp_definition ;
    END LOOP;
END;
$$
LANGUAGE plpgsql;

SELECT * FROM ReplaceViewWithMaterialized();