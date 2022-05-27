DELETE FROM var_mapping WHERE api_topic_id='FindCPLIDApi';
DELETE FROM api_topic WHERE id='FindCPLIDApi';

INSERT INTO api_topic (id, rest_endpoint) VALUES
    ('FindCPLIDApi', 'http://localhost:8090/cplid');

INSERT INTO var_mapping (api_var, bot_var, api_topic_id, var_type) VALUES
    ('plz', 'postalCode', 'FindCPLIDApi', 'IN'),
    ('ort', 'place', 'FindCPLIDApi', 'IN'),
    ('ortsteil', 'district', 'FindCPLIDApi', 'IN'),
    ('strasse', 'street', 'FindCPLIDApi', 'IN'),
    ('hsnr', 'streetNumber', 'FindCPLIDApi', 'IN'),
    ('hsnrZusatz', 'streetSuffix', 'FindCPLIDApi', 'IN'),
    ('cplID', 'cplID', 'FindCPLIDApi', 'OUT');