package br.ths.configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatusCompanyDao {
	
	public static Boolean getStatus(String name) throws Exception {
        String sql = "select c.status as status"
                + " from status_company c"
                + " where c.name = ? ";
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        pst.setString(1,name);
        ResultSet rs = pst.executeQuery();
        rs.next();
        Boolean status = rs.getBoolean("status");
        pst.close();
        Conexao.close();
        return status;
    }
}
