import java.sql.*;
import java.util.*;

/**
 * The class implements methods of the video and bookstore database
 * interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the
 * Connection object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case
 * that an error occurs in the database, then the method should print an
 * error message and call System.exit(-1);
 */
public class StoreApplication {
	 /* Return a list of phone numbers of customers, given a first name and
	 * last name.
	 */
	/*copy II*/
	public List<String> getCustomerPhoneFromFirstLastName(Connection connection,
		String firstName, String lastName) {
		List<String> result = new ArrayList<String>();
		String sqlString = "Select x.phone from dv_address x, mg_customers y where y.last_name = '" + lastName + "' and y.first_name = '" + firstName + "' and y.address_id = x.address_id";
		try(Statement stmt = connection.createStatement())
		{
			ResultSet RS = stmt.executeQuery(sqlString);
			while(RS.next()){
				result.add(RS.getString("phone"));
			}
		}
		catch (SQLException e){
			System.err.println(e);
                	//System.exit(-1);
		}
		{
		}
		return result;
	}

	/**
	 * Return list of film titles whose length is is equal to or greater
	 * than the minimum length, and less than or equal to the maximum
	 * length.
	 */
	public List<String> getFilmTitlesBasedOnLengthRange(Connection connection,
		int minLength, int maxLength) {
		List<String> result = new ArrayList<String>();
		String sqlString = "Select title from dv_film where length <= " + maxLength + " and length >= " + minLength ;
		try(Statement stmt = connection.createStatement())
		{
			ResultSet RS = stmt.executeQuery(sqlString);
			while(RS.next()){
				result.add(RS.getString("title"));
			}
		}
		catch (SQLException e){  
			System.err.println(e);
				//System.exit(-1);

		}
		
		return result;
	}

	/**
	 * Return the number of customers that live in a given district and
	 * have the given active status.
	 */
		public final int countCustomersInDistrict(Connection connection,
			String districtName, boolean active) {
			int result = -1;
			String sqlString = "select count(*) from dv_address x, mg_customers y where x.address_id = y.address_id and y.active =" + active + " and x.district = '" + districtName + "'";
			try(Statement stmt = connection.createStatement())
			{
				ResultSet RS = stmt.executeQuery(sqlString);
				while(RS.next()){
					result = RS.getInt("count");
				}
			}
			catch(SQLException e){
				System.err.print(e);
				//System.exit(-1);
			}

			return result;
		}

	/**
	 * Add a film to the inventory, given its title, description,
	 * length, and rating.
	 *
	 * Your query will need to cast the rating parameter to the
	 * enumerated type mpaa_rating. Whereas an uncast parameter is
	 * simply a question mark, casting would look like ?::mpaa_rating 
	 */ /*preparedStatement = connection.prepareStatement("INSERT INTO Person (name, email, birthdate, photo) VALUES (?, ?, ?, ?)");*/
	public void insertFilmIntoInventory(Connection connection, String
		title, String description, int length, String rating)
	{
		String sqlString = "insert into dv_film(title, description, length, rating) VALUES('" + title +"', '"+ description +"',"+ length +",'"+ rating +"')";
		try(PreparedStatement stmt = connection.prepareStatement(sqlString)){
			stmt.executeUpdate();
			System.out.println("successfully inserted movie!");
		}
		catch(SQLException e){
			System.out.print(e);
		}
		

	}

	/**
	 * Constructor
	 */
	public StoreApplication()
	{}

};
