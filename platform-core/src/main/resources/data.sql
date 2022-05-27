INSERT INTO CRITERIA_WEIGHTS (id, AUTOMATION_RATE_WEIGHT, BUSINESS_IMPACT_WEIGHT, CUSTOMER_WAITING_TIME_WEIGHT, END_OF_LIFE_WEIGHT, EXECUTION_FREQUENCY_WEIGHT,
FREQUENCY_OF_REUSE_WEIGHT, FRONTEND_STABILITY_WEIGHT, NUMBER_OF_BOT_RUNNERS_WEIGHT, NUMBER_OF_SYSTEMS_WEIGHT, QUALITY_OF_RESULTS_WEIGHT, REGULATORY_COMPLIANCE_WEIGHT)
SELECT 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT id FROM CRITERIA_WEIGHTS
    WHERE id=1 LIMIT 1);

INSERT INTO RPABOT (topic, business_impact, end_of_life, frontend_stability, number_of_bot_runners, number_of_systems, quality_of_results, regulatory_compliance)
SELECT 'FindCPLIDRobot', 3, 3, 4, 2, 2, 3, 1 FROM DUAL
WHERE NOT EXISTS (SELECT topic from RPABOT
    WHERE topic='FindCPLIDRobot' LIMIT 1);

INSERT INTO RPABOT (topic, business_impact, end_of_life, frontend_stability, number_of_bot_runners, number_of_systems, quality_of_results, regulatory_compliance)
SELECT 'CheckLineOptionsRobot', 2, 2, 3, 2, 2, 1, 1 FROM DUAL
WHERE NOT EXISTS (SELECT topic from RPABOT
    WHERE topic='CheckLineOptionsRobot' LIMIT 1);