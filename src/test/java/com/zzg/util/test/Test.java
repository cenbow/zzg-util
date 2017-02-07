package com.zzg.util.test;



public class Test {
	public static void main(String[] args) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t1.trick_group_id, t1.use_count, IFNULL(t2.recharge_count, 0) AS recharge_count, IFNULL(t2.amount, 0) AS amount\n");
		sql.append("FROM(\n");
		sql.append("	SELECT trick_group_id, COUNT(1) AS use_count\n");
		sql.append("	FROM trick_record\n");
		sql.append("	WHERE 1 = 1\n");

		sql.append("	GROUP BY trick_group_id\n");
		sql.append(") t1 \n");
		sql.append("LEFT OUTER JOIN (\n");
		sql.append("	SELECT trick_group_id, COUNT(1) AS recharge_count, SUM(amount) AS amount\n");
		sql.append("	FROM stats_user_pay t1\n");
		sql.append("	WHERE 1 = 1\n");
		sql.append("	GROUP BY trick_group_id\n");
		sql.append(") t2\n");
		sql.append("ON t1.trick_group_id = t2.trick_group_id\n");
		sql.append("ORDER BY IFNULL(t2.recharge_count, 0)/t1.use_count DESC\n");
		System.out.println(sql.toString());

	}
}
