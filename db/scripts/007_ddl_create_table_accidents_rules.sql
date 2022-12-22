CREATE TABLE accidents_rules (
    accident_id INTEGER NOT NULL REFERENCES accident(id),
    rule_id INTEGER NOT NULL REFERENCES rule(id),
    PRIMARY KEY (accident_id, rule_id)
);