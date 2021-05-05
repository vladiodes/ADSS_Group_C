package DataAccessLayer;

import DTO.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;

public class  CategoryDAO extends DAO<CategoryDTO> {
    private String NameCol="Name",ParentCat="ParentCategoryID",
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
        CategoryDTO output=null;
        ResultSet rs=get("ID",String.valueOf(id));
        try {
            rs.last();
            if (rs.getRow() == 0)
                return null;
            rs.beforeFirst();
            output = new CategoryDTO(rs.getInt("ID"), rs.getString(NameCol), rs.getInt(ParentCat));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(output==null)
            return null;
        output.itemIDS=Repository.getInstance().getCategoryItems(output.id);
        output.categoriesIDS=Repository.getInstance().getIds("SELECT C2.ID from Category as C1, Category as C2\n" +
                "Where C2.ParentCategoryID=C1.ID AND C1.ID=" + output.id + ";");
        return output;
    }

    public int delete(CategoryDTO dto) {
        return delete("ID",String.valueOf(dto.id));
    }
}
