create or replace view transaction_account_deposit(id, user_id, deposit) as
SELECT ta.id,
       ta.user_id,
       COALESCE(((SELECT sum(t.amount) AS sum
                  FROM transaction t
                  WHERE t.destination_id = ta.id
                    AND t.user_id::text = ta.user_id::text
                    AND ta.is_deleted = false
                  GROUP BY t.destination_id)) - ((SELECT sum(t.amount) AS sum
                                                  FROM transaction t
                                                  WHERE t.source_id = ta.id
                                                    AND t.user_id::text = ta.user_id::text
                                                    AND ta.is_deleted = false
                                                  GROUP BY t.source_id)), 0::double precision) AS deposit
FROM transaction_account ta
WHERE ta.is_deleted = false;

alter table transaction_account_deposit
    owner to "user";


