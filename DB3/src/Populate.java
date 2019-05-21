import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

class FileRead {

    File path;

    public FileRead(String str) {
        path = new File(str);
    }

    public String[] openFile() throws IOException {
        FileReader filereader = new FileReader(path);
        BufferedReader textreader = new BufferedReader(filereader);
        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            textData[i] = textreader.readLine();
        }
        textreader.close();
        return textData;
    }

    public int readLines() throws IOException {
        FileReader filereader = new FileReader(path);
        BufferedReader bf = new BufferedReader(filereader);
        String line;
        int numberOflines = 0;
        while ((line = bf.readLine()) != null) {
            numberOflines++;
        }
        bf.close();
        return numberOflines;
    }
}

public class Populate {

    public static void main(String[] args) {
        Populate p=new Populate();
        p.run(args);

    }
    public void run(String[] args)
    {
        Connection con = null;
        try {
            System.out.println("Connection being established to database");
            con = openConnection();
            System.out.println("Database connected\n\n");
            //System.out.println(args[8]);


            populateMovie_Actors(con, args[0]);
            populateMovie_Countries(con, args[1]);
            populateMovie_Directors(con, args[2]);
            populateMovie_Genres(con, args[3]);
            populateMovie_Tags(con, args[4]);
            populateMovies(con, args[5]);
            populateTags(con, args[6]);
            populateUser_RatedMovies(con, args[7]);
            populateUser_TaggedMovies(con, args[8]);
            showTableContent(con);



        } catch (SQLException e) {
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find the database driver");
        } finally {
            // Never forget to close database connection
            closeConnection(con);
        }
    }

    private void populateUser_RatedMovies(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from user_ratedmovies table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM USER_RATEDMOVIES");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating user_ratedmovies table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO USER_RATEDMOVIES VALUES(?,?,?,?,?,?,?,?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                stmt.setString(1, words[0]);
                stmt.setString(2, words[1]);
                stmt.setString(3, words[2]);
                stmt.setString(4, words[3]);
                stmt.setString(5, words[4]);
                stmt.setString(6, words[5]);
                stmt.setString(7, words[6]);
                stmt.setString(8, words[7]);
                stmt.setString(9, words[8]);
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data populated in user_ratedmovies table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateUser_TaggedMovies(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from user_taggedmovies table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM USER_TAGGEDMOVIES");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating user_taggedmovies table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO USER_TAGGEDMOVIES VALUES(?,?,?,?,?,?,?,?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                stmt.setString(1, words[0]);
                stmt.setString(2, words[1]);
                stmt.setString(3, words[2]);
                stmt.setString(4, words[3]);
                stmt.setString(5, words[4]);
                stmt.setString(6, words[5]);
                stmt.setString(7, words[6]);
                stmt.setString(8, words[7]);
                stmt.setString(9, words[8]);
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data populated in user_taggedmovies table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateTags(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from tags table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM TAGS");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating tags table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO TAGS VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data Populated in tags table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }
    private void populateMovie_Actors(Connection con, String arg) throws SQLException {

        System.out.println("Deleting from movie_actors table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIE_ACTORS");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] words = null;

        System.out.println("Started Re-populating movie_actors table....");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_ACTORS VALUES(?,?,?,?)");
        for(int i = 1; i < str.length; i++)
        {
            words = str[i].split("\\s");
            for(int j = 3; j < words.length-1; j++)
            {
                words[2] += " " + words[j] + " ";
            }
            words[3] = words[words.length-1];
            stmt.setString(1, words[0]);
            stmt.setString(2, words[1]);
            stmt.setString(3, words[2]);
            stmt.setString(4, words[3]);
            stmt.executeUpdate();
        }
        stmt.close();
        stmt1.close();
        System.out.println("Data populated in movie_actors table!\n\n");
    }

    private void populateMovie_Countries(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from movie_countries table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIE_COUNTRIES");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started re-populating movie_countries table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_COUNTRIES VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data populated in movie_countries table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateMovie_Directors(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from movie_directors table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIE_DIRECTORS");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;

            System.out.println("Started Re-populating movie_directors table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_DIRECTORS VALUES(?,?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\t");
                for(int j = 3; j < words.length; j++)
                {
                    words[2] += " " + words[j] + " ";
                }
                stmt.setString(1, words[0]);
                stmt.setString(2, words[1]);
                stmt.setString(3, words[2]);
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data Populated in movie_directors table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateMovie_Genres(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from movie_genres table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIE_GENRES");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating movie_genres table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_GENRES VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data Populated in movie_genres table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateMovie_Tags(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from movie_tags table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIE_TAGS");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating movie_tags table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_TAGS VALUES(?,?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 3; j < words.length; j++)
                {
                    words[2] += " " + words[j] + " ";
                }

                if(words.length<3)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                    stmt.setString(3, words[2]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data populated in movie_tags table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateMovies(Connection con, String arg) throws SQLException {
        System.out.println("Deleting from movie table....");
        Statement stmt1 = con.createStatement();
        stmt1.executeQuery("DELETE FROM MOVIES");
        FileRead fR = new FileRead("Movielens/"+arg);
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;
            System.out.println("Started Re-populating movie table....");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIES VALUES(?,?,?,?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\t");
                stmt.setString(1, words[0]);
                stmt.setString(2, words[1]);
                stmt.setString(3, words[5]);
                stmt.setString(4, words[17]);
                stmt.setString(5, words[18]);
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
            System.out.println("Data populated in movie table!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void showTableContent(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM USER_TAGGEDMOVIES");

           /*
           We use ResultSetMetaData.getColumnCount() to know the number columns
           that are contained.
           */
        ResultSetMetaData meta = result.getMetaData();
        for (int col = 1; col <= meta.getColumnCount(); col++) {
            System.out.println("Column" + col + ": " + meta.getColumnName(col) +
                    "\t, Type: " + meta.getColumnTypeName(col));
        }

           /*
           Every time we call ResultSet.next(), its internal cursor will advance
           one tuple. By calling this method continuously, we can iterate through
           the whole ResultSet.
           */
        while (result.next()) {
            for (int col = 1; col <= meta.getColumnCount(); col++) {
                System.out.print("\"" + result.getString(col) + "\",");
            }
            System.out.println();
        }

          /*
          It is always a good practice to close a statement as soon as we
          no longer use it.
          */
        stmt.close();
    }

    /*
    @return a database connection
    @throws SQLException when there is an error when trying to connect database
    @throws ClassNotFoundException when the database driver is not found.
    */
    private Connection openConnection() throws SQLException, ClassNotFoundException {
        // Load the Oracle database driver
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        /*
        Here is the information needed when connecting to a database
        server. These values are now hard-coded in the program. In
        general, they should be stored in some configuration file and
        read at run time.
        */
        String host = "localhost";
        String port = "1521";
        String dbName = "orcl";
        String userName = "scott";
        String password = "tiger";

        // Construct the JDBC URL
        String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
        return DriverManager.getConnection(dbURL, userName, password);
    }

    /*
    Close the database connection
    @param con
    */
    private void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }
}