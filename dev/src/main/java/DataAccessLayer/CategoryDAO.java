package DataAccessLayer;

import DTO.CategoryDTO;

import java.sql.*;

public class CategoryDAO extends DAO<CategoryDTO> {
    String NameCol="Name",ParentCat="ParentCategoryID",
    INSERT_SQL=String.format("INSERT INTO %s (%s,%s) VALUES(?,?)",tableName,NameCol,ParentCat),
    UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,NameCol,ParentCat);


    public CategoryDAO(){
        super("Category");
    }
    @Override
    public int insert(CategoryDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,dto.name);
            ps.setString(2, String.valueOf(dto.fatherCatID));
            ps.executeUpdate();
            id=getInsertedID(con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }
    @Override
    public int update(CategoryDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1,dto.name);
            ps.setString(2, String.valueOf(dto.fatherCatID));
            ps.setString(3, String.valueOf(dto.id));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public CategoryDTO get(int id) {
        ResultSet rs=get("ID",String.valueOf(id));
        return null;
        //@TODO: continue from here
    }

    public int delete(CategoryDTO dto) {
        return delete("ID",String.valueOf(dto.id));
    }
}
