INSERT INTO company(id, creation_date,name) VALUES (1, now(), 'Mari Ruas Cabeleireira');
INSERT INTO branch_company( id, creation_date, description, company_id)   VALUES (1, now(), 'Unidade de Lagoa Vermelha', 1);
INSERT INTO cashier(id, balance, description, branch_company_id)   VALUES (1, 0, 'Caixa matriz', 1);
INSERT INTO user_branch(
            login, password, type)
    VALUES ('admin', '9B6B447FC336D4F7046BF40823859E32DC0E0BC6156FD0A965E8A061BFE5C28F', 'admin');


