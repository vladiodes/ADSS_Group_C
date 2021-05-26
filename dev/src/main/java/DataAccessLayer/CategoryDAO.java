package DataAccessLayer;

import DTO.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class  CategoryDAO extends DAOV1<CategoryDTO> {
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
        CategoryDTO output = null;
        Connection con = Repository.getInstance().connect();
        ResultSet rs = get("ID", String.valueOf(id), con);
        try {
            if (!rs.isClosed())
                output = new CategoryDTO(rs.getInt("ID"), rs.getString(NameCol), rs.getInt(ParentCat));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        if (output == null)
            return null;
        String query = String.format("Select Item.ID as ID\n" +
                "from Category,Item\n" +
                "where Category.ID=Item.CategoryID AND Category.ID=%d;", output.id);

        output.itemIDS = Repository.getInstance().getIds(query);
        output.categoriesIDS = Repository.getInstance().getIds("SELECT C2.ID from Category as C1, Category as C2\n" +
                "Where C2.ParentCategoryID=C1.ID AND C1.ID=" + output.id + ";");
        return output;
    }

    public int delete(CategoryDTO dto) {
        return delete("ID",String.valueOf(dto.id));
    }

    public List<CategoryDTO> getAllCategories(){
        Connection con=Repository.getInstance().connect();
        ResultSet rs=super.getAll(con);
        ArrayList<CategoryDTO> output=new ArrayList<>();
        try {
            while (rs.next())
                output.add(new CategoryDTO(rs.getInt("ID"), rs.getString(NameCol), rs.getInt(ParentCat)));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);

        }
        for(CategoryDTO cat:output) {
            String query = String.format("Select Item.ID as ID\n" +
                    "from Category,Item\n" +
                    "where Category.ID=Item.CategoryID AND Category.ID=%d;", cat.id);
            cat.itemIDS = Repository.getInstance().getIds(query);
            cat.categoriesIDS = Repository.getInstance().getIds("SELECT C2.ID from Category as C1, Category as C2\n" +
                    "Where C2.ParentCategoryID=C1.ID AND C1.ID=" + cat.id + ";");
        }
        return output;
    }
}
