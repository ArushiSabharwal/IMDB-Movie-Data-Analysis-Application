import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.ArrayList;

public class HW3 {
    private JFrame frame;
    static Connection con = null;
    static ResultSet result = null;
    static PreparedStatement prepStatement = null;

    //Lists respectively the attributes selected - For display (Not Selection)
    private JList<String> genreJList, countryJList, actorJList, directorJList, tagJList, movieQJList, userQJList;


    DefaultListModel genreModel = new DefaultListModel();
    DefaultListModel countryModel = new DefaultListModel();
    DefaultListModel actorModel = new DefaultListModel();
    DefaultListModel directorModel = new DefaultListModel();
    DefaultListModel tagModel = new DefaultListModel();
    DefaultListModel movieModel = new DefaultListModel();
    DefaultListModel userModel = new DefaultListModel();

   // AutoCompleteDecorator decorator;

    ArrayList<String> genreList = new ArrayList();
    ArrayList<String> countryList = new ArrayList();
    ArrayList<String> actorList = new ArrayList();
    ArrayList<String> directorList = new ArrayList();
    ArrayList<String> tagList = new ArrayList();
    ArrayList<String> tagList_1 = new ArrayList();
    ArrayList<String> movieList = new ArrayList();
    ArrayList<String> userList = new ArrayList();
    private String start, stop, movie="", movieA="", movieC="",movieD="",movieT="", tagM="", value, movieS="", user="", finalMovieList="";
    //private JTextField textField;
    //private JTextField textField_1;
    private JComboBox comboBox, comboBox_6;
    private JTextField textField_2;
    JTextArea textArea;
    private com.toedter.calendar.JYearChooser jYearChooser1; //From:
    private com.toedter.calendar.JYearChooser jYearChooser2; //To:

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HW3 window = new HW3();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public HW3() {
        initialize();
        run1();
    }

    /**
     * Initialize the contents of the frame.
     */

    public void run1(){

        try {
            con = openConnection();
            populateGenre();
        } catch (SQLException e) {
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find the database driver");
        } finally {
            //closeConnection(con);
            // Never forget to close database connection
        }
    }



    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(38, 101, 240)); //0, 102, 153
        frame.setBounds(100, 100, 850, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Connection closed");
                closeConnection(con);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(12, 0, 574, 39);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Movie Attributes");
        lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        lblNewLabel.setBounds(0, 0, 569, 39);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        panel_1.setBounds(588, 0, 244, 39);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Movie Results");
        lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 20));
        lblNewLabel_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(0, 0, 244, 39);
        panel_1.add(lblNewLabel_1);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.scrollbar);
        panel_3.setBounds(12, 38, 141, 33);
        frame.getContentPane().add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Genres");
        lblNewLabel_2.setBounds(0, 0, 141, 31);
        panel_3.add(lblNewLabel_2);
        lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 70, 141, 215);
        frame.getContentPane().add(scrollPane);

        genreJList = new JList<>();

        genreJList.setBounds(0, 184, 139, -184);
        JPanel genrePanel = new JPanel();
        scrollPane.setViewportView(genrePanel);
        genrePanel.setBackground(new Color(240, 240, 240));
        genrePanel.setForeground(new Color(51, 51, 255)); //0, 128, 128
        //genrePanel.setLayout(null);
        genrePanel.add(genreJList);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.LIGHT_GRAY);
        panel_2.setBounds(12, 283, 141, 27);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("Movie Year");
        lblNewLabel_3.setBounds(0, 0, 141, 27);
        lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblNewLabel_3);

        JPanel panel_4 = new JPanel();
        panel_4.setBounds(12, 311, 141, 58);
        frame.getContentPane().add(panel_4);
        panel_4.setLayout(null);

        JLabel lblNewLabel_4 = new JLabel("From");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_4.setFont(new Font("Calibri", Font.PLAIN, 10));
        lblNewLabel_4.setBounds(0, 0, 56, 29);
        panel_4.add(lblNewLabel_4);

        //Year selector initialize
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jYearChooser1.setBounds(62, 2, 79, 23);
        panel_4.add(jYearChooser1);
        jYearChooser1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                movie ="";
                movieC="";
                movieA="";
                movieD="";
                movieT="";
                movieS="";
                countryModel.clear();
                actorModel.clear();
                directorModel.clear();
                tagModel.clear();
                movieModel.clear();
                userModel.clear();
                textField_2.setText("");
                textArea.setText("");
            }
        });

        JLabel lblNewLabel_5 = new JLabel("To");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setFont(new Font("Calibri", Font.PLAIN, 10));
        lblNewLabel_5.setBounds(0, 30, 56, 23);
        panel_4.add(lblNewLabel_5);

        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        panel_4.add(jYearChooser2);
        jYearChooser2.setBounds(62, 31, 79, 22);
        jYearChooser2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                movie ="";
                movieC="";
                movieA="";
                movieD="";
                movieT="";
                movieS="";
                countryModel.clear();
                actorModel.clear();
                directorModel.clear();
                tagModel.clear();
                movieModel.clear();
                userModel.clear();
                textField_2.setText("");
                textArea.setText("");
            }
        });

        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.LIGHT_GRAY);
        panel_5.setBounds(154, 38, 141, 33);
        frame.getContentPane().add(panel_5);
        panel_5.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("Country");
        lblNewLabel_6.setBounds(0, 0, 141, 33);
        panel_5.add(lblNewLabel_6);
        lblNewLabel_6.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.activeCaption);
        panel_7.setBounds(12, 395, 574, 45);
        frame.getContentPane().add(panel_7);
        panel_7.setLayout(null);

        JLabel lblNewLabel_7 = new JLabel("Search Between Attributes' Values:");
        lblNewLabel_7.setFont(new Font("Calibri", Font.BOLD, 20));
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_7.setBounds(0, 0, 357, 43);
        panel_7.add(lblNewLabel_7);

        comboBox = new JComboBox();
        comboBox.setFont(new Font("Calibri", Font.PLAIN, 15));
        comboBox.setBounds(332, 1, 242, 45);
        comboBox.setMaximumRowCount(3);
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select AND, OR between attributes", "AND", "OR" }));
        panel_7.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(countryList.isEmpty()) {
                    movie="";
                } else if (actorList.isEmpty()) {
                    movieC = "";
                } else if (directorList.isEmpty()) {
                    movieA = "";
                } else if (tagList.isEmpty()) {
                    movieD = "";
                } else if (movieList.isEmpty()) {
                    movieT = "";
                    movieS = "";
                } else if (userList.isEmpty()) {
                    user = "";
                }
            }
        });

        JButton btnNewButton = new JButton("Get Country");
        btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnNewButton.setBackground(SystemColor.textHighlight);
        btnNewButton.setBounds(22, 370, 120, 25);
        frame.getContentPane().add(btnNewButton);
        //Get Country button
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                populateCountry(evt);
            }
        });

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(154, 70, 141, 299);
        frame.getContentPane().add(scrollPane_1);

        countryJList = new JList();
        JPanel panel_6 = new JPanel();
        scrollPane_1.setViewportView(panel_6);
        panel_6.add(countryJList);

        JPanel panel_8 = new JPanel();
        panel_8.setLayout(null);
        panel_8.setBackground(Color.LIGHT_GRAY);
        panel_8.setBounds(295, 38, 141, 33);
        frame.getContentPane().add(panel_8);

        JLabel lblCast = new JLabel("Cast");
        lblCast.setHorizontalAlignment(SwingConstants.CENTER);
        lblCast.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblCast.setBounds(0, 0, 141, 33);
        panel_8.add(lblCast);

        JScrollPane scrollPane_5 = new JScrollPane();
        scrollPane_5.setBounds(295, 97, 141, 118);
        frame.getContentPane().add(scrollPane_5);

        JPanel panel_9 = new JPanel();
        scrollPane_5.setViewportView(panel_9);
        //panel_9.setLayout(null);

        actorJList = new JList();
        panel_9.add(actorJList);

        JScrollPane scrollPane_6 = new JScrollPane();
        scrollPane_6.setBounds(295, 265, 141, 104);
        frame.getContentPane().add(scrollPane_6);

        JPanel panel_10 = new JPanel();
        scrollPane_6.setViewportView(panel_10);
        //panel_10.setLayout(null);

        directorJList = new JList();
        panel_10.add(directorJList);

        JButton btnGetCast = new JButton("Get Cast");
        btnGetCast.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnGetCast.setBackground(SystemColor.textHighlight);
        btnGetCast.setBounds(164, 370, 120, 25);
        frame.getContentPane().add(btnGetCast);
        //Get Cast Button
        btnGetCast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                populateActor(evt);
            }
        });

        JPanel panel_11 = new JPanel();
        panel_11.setLayout(null);
        panel_11.setBackground(Color.LIGHT_GRAY);
        panel_11.setBounds(437, 38, 149, 33);
        frame.getContentPane().add(panel_11);

        JLabel lblTagIdsAnd = new JLabel("Tag Ids and Values");
        lblTagIdsAnd.setHorizontalAlignment(SwingConstants.CENTER);
        lblTagIdsAnd.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblTagIdsAnd.setBounds(0, 0, 151, 33);
        panel_11.add(lblTagIdsAnd);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(437, 72, 149, 250);
        frame.getContentPane().add(scrollPane_2);

        JPanel panel_12 = new JPanel();
        scrollPane_2.setViewportView(panel_12);

        tagJList = new JList();
        panel_12.add(tagJList);

        JButton btnGetTags = new JButton("Get Tags");
        btnGetTags.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnGetTags.setBackground(SystemColor.textHighlight);
        btnGetTags.setBounds(305, 370, 120, 25);
        frame.getContentPane().add(btnGetTags);
        //Get Tags Button
        btnGetTags.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                populateTags(evt);
            }
        });

        JScrollPane scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(588, 41, 244, 354);
        frame.getContentPane().add(scrollPane_3);

        JPanel panel_13 = new JPanel();
        scrollPane_3.setViewportView(panel_13);

        movieQJList = new JList();
        panel_13.add(movieQJList);

        JPanel panel_14 = new JPanel();
        panel_14.setBounds(437, 324, 149, 71);
        frame.getContentPane().add(panel_14);
        panel_14.setLayout(null);

        JLabel lblTagWeight = new JLabel("Tag Weight");
        lblTagWeight.setBounds(12, 5, 55, 23);
        lblTagWeight.setHorizontalAlignment(SwingConstants.CENTER);
        lblTagWeight.setFont(new Font("Calibri", Font.PLAIN, 10));
        panel_14.add(lblTagWeight);

        JLabel lblValue = new JLabel("Value");
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblValue.setFont(new Font("Calibri", Font.PLAIN, 10));
        lblValue.setBounds(12, 35, 55, 23);
        panel_14.add(lblValue);

        comboBox_6 = new JComboBox();
        comboBox_6.setBounds(79, 4, 70, 24);
        comboBox_6.setMaximumRowCount(3);
        comboBox_6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", ">", "<" }));
        panel_14.add(comboBox_6);

        textField_2 = new JTextField();
        textField_2.setBounds(70, 35, 79, 23);
        panel_14.add(textField_2);
        textField_2.setColumns(10);
        //Value Field of Tag Weight
        textField_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                value = textField_2.getText();
            }
        });

        JScrollPane scrollPane_7 = new JScrollPane();
        scrollPane_7.setBounds(12, 472, 574, 141);
        frame.getContentPane().add(scrollPane_7);

        textArea = new JTextArea();
        scrollPane_7.setViewportView(textArea);

        JPanel panel_15 = new JPanel();
        panel_15.setLayout(null);
        panel_15.setBackground(SystemColor.activeCaption);
        panel_15.setBounds(588, 399, 244, 39);
        frame.getContentPane().add(panel_15);

        JLabel lblUserResults = new JLabel("User Results");
        lblUserResults.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserResults.setFont(new Font("Calibri", Font.BOLD, 20));
        lblUserResults.setBackground(SystemColor.activeCaption);
        lblUserResults.setBounds(0, 0, 244, 39);
        panel_15.add(lblUserResults);

        JScrollPane scrollPane_4 = new JScrollPane();
        scrollPane_4.setBounds(588, 440, 244, 175);
        frame.getContentPane().add(scrollPane_4);

        JPanel panel_16 = new JPanel();
        scrollPane_4.setViewportView(panel_16);
        userQJList = new JList();
        panel_16.add(userQJList);

        JButton btnNewButton_1 = new JButton("Execute Movie Query");
        btnNewButton_1.setBackground(new Color(64, 224, 208));
        //Execute Movie Query Button
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                populateMovieSearch(evt);
                textArea.setText(finalMovieList); //Displays Query in TextArea
            }
        });
        btnNewButton_1.setBounds(84, 626, 158, 28);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnExecuteUserQuery = new JButton("Execute User Query");
        btnExecuteUserQuery.setBounds(329, 626, 158, 28);
        btnExecuteUserQuery.setBackground(new Color(64, 224, 208));
        //Execute User Query Button
        btnExecuteUserQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                user="";
                userModel.clear();
                textArea.setText("");
                populateUserSearch(evt);
                textArea.setText(user); //Displays Query in TextArea
            }
        });
        frame.getContentPane().add(btnExecuteUserQuery);

        JButton button = new JButton("Get Directors");
        button.setFont(new Font("Calibri", Font.PLAIN, 15));
        button.setBackground(SystemColor.textHighlight);
        button.setBounds(307, 216, 117, 25);
        //Get Directors Button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                populateDirector(evt);
            }
        });
        frame.getContentPane().add(button);

        JLabel lblNewLabel_8 = new JLabel("Actor/Actress");
        lblNewLabel_8.setForeground(Color.WHITE);
        lblNewLabel_8.setBounds(307, 70, 117, 26);
        frame.getContentPane().add(lblNewLabel_8);
        lblNewLabel_8.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label = new JLabel("Director");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri", Font.PLAIN, 15));
        label.setBackground(Color.WHITE);
        label.setBounds(327, 243, 84, 18);
        frame.getContentPane().add(label);

        JLabel lblNewLabel_9 = new JLabel("Query Display:");
        lblNewLabel_9.setFont(new Font("Calibri", Font.BOLD, 15));
        lblNewLabel_9.setForeground(Color.WHITE);
        lblNewLabel_9.setBackground(Color.DARK_GRAY);
        lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_9.setBounds(12, 440, 141, 29);
        frame.getContentPane().add(lblNewLabel_9);
    }

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

    /**
     * Close the database connection
     * @param con
     */
    private void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    private void populateGenre() {

        String genre = "SELECT DISTINCT G.GENRE FROM MOVIE_GENRES G";
        try {
            ResultSet rS = null;
            prepStatement=con.prepareStatement(genre);
            rS = prepStatement.executeQuery(genre);
            //showResultSet(rS);
            while(rS.next())
            {
                if(!genreModel.contains(rS.getString("genre")))
                {
                    genreModel.addElement(rS.getString("genre"));
                }
            }
            prepStatement.close();
            rS.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        genreJList.setModel(genreModel);

        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                movie ="";
                movieC="";
                movieA="";
                movieD="";
                movieT="";
                movieS="";
                countryModel.clear();
                actorModel.clear();
                directorModel.clear();
                tagModel.clear();
                movieModel.clear();
                userModel.clear();
                textField_2.setText("");
                textArea.setText("");
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                genreList.clear();
                for (int i = 0; i< selections.length; i++) {
                    genreList.add(selectionValues[i].toString());
                }
            }
        };

       genreJList.addListSelectionListener(selectionListener);
    }

    private void populateCountry(ActionEvent evt) {
        countryModel.clear();
        if(genreList.size()!=0)
        {
            String country = "";
            String bAttr = "";

            if(comboBox.getSelectedIndex()==1)
            {
                bAttr = "INTERSECT";
            }
            else
            {
                if(comboBox.getSelectedIndex()==0 || comboBox.getSelectedIndex()==2) //By Default it is OR
                {
                    bAttr = "UNION";
                }
            }

            if(genreList.size() == 0) //Selects all the countries in case User has not selected any country
            {
                int start = 0;
                int end = genreJList.getModel().getSize()-1;
                genreJList.setSelectionInterval(start, end);
                genreList = (ArrayList<String>) genreJList.getSelectedValuesList();
            }

            //Genre Within attributes
            start = String.valueOf(jYearChooser1.getYear());
            stop = String.valueOf(jYearChooser2.getYear()); //jYearChooser.getYear() returns int, need to convert to String
            System.out.println("start and stop values are: " + start  + " " + stop);
            int i=0;
            for(i=0;i<genreList.size()-1;i++)
            {
                movie += "SELECT DISTINCT M.MOVIEID \nFROM MOVIE_GENRES MG, MOVIES M\nWHERE M.MOVIEID=MG.MOVIEID AND M.MYEAR >="+ start +" AND M.MYEAR <="+stop+" AND MG.GENRE = '"+genreList.get(i)+"'\n"+bAttr+"\n";
               }

            movie += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIES M\nWHERE M.MOVIEID=MG.MOVIEID AND M.MYEAR >="+ start +" AND M.MYEAR <="+stop+" AND MG.GENRE = '"+genreList.get(i)+"'";

            country += "SELECT DISTINCT C.COUNTRY FROM MOVIE_COUNTRIES C \n WHERE C.MOVIEID IN ( " + movie +" ) \n";

            ResultSet rS = null;

            try
            {
                prepStatement = con.prepareStatement(country);
                rS =prepStatement.executeQuery(country);


                if(rS.next() == false) {
                    countryModel.addElement("No countries found for the given criteria"); //Displays this in the Country panel, if No Rows selected in the Database

                }
                rS =prepStatement.executeQuery(country);
                while(rS.next())
                {

                    if(!countryModel.contains(rS.getString("COUNTRY")))
                    {
                        countryModel.addElement(rS.getString("COUNTRY"));
                    }
                }
                prepStatement.close();
                rS.close();

            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            countryJList.setModel(countryModel);

            ListSelectionListener selectionListener = new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    movieC ="";
                    movieA = "";
                    movieD = "";
                    movieS = "";
                    movieT = "";
                    actorModel.clear();
                    directorModel.clear();
                    tagModel.clear();
                    movieModel.clear();
                    userModel.clear();
                    textField_2.setText("");
                    textArea.setText("");
                    JList list = (JList) listSelectionEvent.getSource();
                    int selections[] = list.getSelectedIndices();
                    Object selectionValues[] = list.getSelectedValues();
                    countryList.clear();
                    for (int i = 0; i< selections.length; i++) {
                        countryList.add(selectionValues[i].toString());
                    }
                }
            };


            countryJList.addListSelectionListener(selectionListener);
        }
    }

    private void populateActor(ActionEvent evt) {

        actorModel.clear();
        String actor = "";
        //String director = "";
        String bAttr = "";
        if(comboBox.getSelectedIndex()==1)
        {
            bAttr = "INTERSECT";
        }
        else
        {
            if(comboBox.getSelectedIndex()==0 || comboBox.getSelectedIndex()==2) //By Default OR
            {
                bAttr = "UNION";
            }
        }

        if(countryList.size() == 0) //Selects all the countries in case User has not selected any country
        {
            int start = 0;
            int end = countryJList.getModel().getSize()-1;
            countryJList.setSelectionInterval(start, end);
            countryList = (ArrayList<String>) countryJList.getSelectedValuesList();
        }


        //Genre Within attributes
        int i=0;

        for(i=0;i<countryList.size()-1;i++)
        {
            actor += "SELECT DISTINCT A.ACTORNAME\nFROM MOVIE_ACTORS A, MOVIE_COUNTRIES MMC\nWHERE A.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+countryList.get(i)+"' AND A.MOVIEID IN("+movie+")"+"\n"+bAttr+"\n";
            movieC += "SELECT DISTINCT MC.MOVIEID\nFROM MOVIE_COUNTRIES MC\nWHERE MC.COUNTRY='"+countryList.get(i)+"' AND MC.MOVIEID IN("+movie+")"+"\n"+bAttr+"\n";

        }
        actor += "SELECT DISTINCT A.ACTORNAME\nFROM MOVIE_ACTORS A, MOVIE_COUNTRIES MMC\nWHERE A.MOVIEID= MMC.MOVIEID AND MMC.COUNTRY='"+countryList.get(i)+"' AND A.MOVIEID IN("+movie+")"+"\n";
        movieC += "SELECT DISTINCT MC.MOVIEID\nFROM MOVIE_COUNTRIES MC\nWHERE MC.COUNTRY='"+countryList.get(i)+"' AND MC.MOVIEID IN("+movie+")"+"\n";
        ResultSet rS = null;

        try
        {
            prepStatement = con.prepareStatement(actor);
            rS =prepStatement.executeQuery(actor);

            if(rS.next() == false) {
                actorModel.addElement("No actors found for the given criteria");

            }
            rS =prepStatement.executeQuery(actor);

            while(rS.next())
            {
                actorModel.addElement(rS.getString("ACTORNAME"));
            }
            prepStatement.close();
            rS.close();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        actorJList.setModel(actorModel);

        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                movieA ="";
                movieD = "";
                movieS = "";
                movieT = "";
                directorModel.clear();
                tagModel.clear();
                movieModel.clear();
                userModel.clear();
                textField_2.setText("");
                textArea.setText("");
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                actorList.clear();
                for (int i = 0; i< selections.length; i++) {
                    actorList.add(selectionValues[i].toString());
                }
            }
        };

        actorJList.addListSelectionListener(selectionListener);
    }

    private void populateDirector(ActionEvent evt) {
        directorModel.clear();
        String director = "";
        String bAttr = "";

        if(comboBox.getSelectedIndex()==1)
        {
            bAttr = "INTERSECT";
        }
        else
        {
            if(comboBox.getSelectedIndex()==0 || comboBox.getSelectedIndex()==2) //By Default Takes OR
            {
                bAttr = "UNION";
            }
        }

        if(actorList.size() == 0)
        {
            int start = 0;
            int end = actorJList.getModel().getSize()-1;
            actorJList.setSelectionInterval(start, end);
            actorList = (ArrayList<String>) actorJList.getSelectedValuesList();
        }

        //Genre Within attributes
        int i=0;

        for(i=0;i<actorList.size()-1;i++)
        {
            director += "SELECT DISTINCT D.DIRECTORNAME\nFROM MOVIE_DIRECTORS D, MOVIE_ACTORS MA\nWHERE D.MOVIEID= MA.MOVIEID AND MA.ACTORNAME='"+actorList.get(i)+"' AND D.MOVIEID IN("+movieC+")"+"\n"+bAttr+"\n";
            movieA += "SELECT DISTINCT A.MOVIEID\nFROM MOVIE_ACTORS A\nWHERE A.ACTORNAME='"+actorList.get(i)+"' AND A.MOVIEID IN("+movieC+")"+"\n"+bAttr+"\n";
        }
        director += "SELECT DISTINCT D.DIRECTORNAME\nFROM MOVIE_DIRECTORS D, MOVIE_ACTORS MA\nWHERE D.MOVIEID= MA.MOVIEID AND MA.ACTORNAME='"+actorList.get(i)+"' AND D.MOVIEID IN("+movieC+")"+"\n";
        movieA += "SELECT DISTINCT A.MOVIEID\nFROM MOVIE_ACTORS A\nWHERE A.ACTORNAME='"+actorList.get(i)+"' AND A.MOVIEID IN("+movieC+")"+"\n";

        ResultSet rS = null;

        try
        {
            prepStatement = con.prepareStatement(director);
            rS =prepStatement.executeQuery(director);

            if(rS.next() == false) {
                directorModel.addElement("No directors found for the given criteria");
            }

            rS =prepStatement.executeQuery(director);
            while(rS.next())
            {
                directorModel.addElement(rS.getString("DIRECTORNAME"));
            }
            prepStatement.close();
            rS.close();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        directorJList.setModel(directorModel);

        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                movieD ="";
                movieS = "";
                movieT = "";
                tagModel.clear();
                movieModel.clear();
                userModel.clear();
                textField_2.setText("");
                textArea.setText("");
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                directorList.clear();
                for (int i = 0; i< selections.length; i++) {
                    directorList.add(selectionValues[i].toString());
                }
            }
        };


        directorJList.addListSelectionListener(selectionListener);

    }

    private void populateTags(ActionEvent evt) {
        tagModel.clear();
        String tags = "";
        String bAttr = "";

        if(comboBox.getSelectedIndex()==1)
        {
            bAttr = "INTERSECT";
        }
        else
        {
            if(comboBox.getSelectedIndex()==0 || comboBox.getSelectedIndex()==2)
            {
                bAttr = "UNION";
            }
        }

        if(directorList.size() == 0)
        {
            int start = 0;
            int end = directorJList.getModel().getSize()-1;
            directorJList.setSelectionInterval(start, end);
            directorList = (ArrayList<String>) directorJList.getSelectedValuesList();

        }

        //Genre Within attributes
        int i=0;


        for(i=0;i<directorList.size()-1;i++)
        {
            tags += "SELECT DISTINCT T.TAGID, T.VALUE\nFROM TAGS T, MOVIE_TAGS MT, MOVIE_DIRECTORS MD\nWHERE MD.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MD.DIRECTORNAME = '"+ directorList.get(i)+"' AND MT.MOVIEID IN("+movieA+") \n"+bAttr+"\n";
            movieD += "SELECT DISTINCT D.MOVIEID\nFROM MOVIE_DIRECTORS D\nWHERE D.DIRECTORNAME='"+directorList.get(i)+"' AND D.MOVIEID IN("+movieA+")"+"\n"+bAttr+"\n";
        }
        tags += "SELECT DISTINCT T.TAGID, T.VALUE\nFROM TAGS T, MOVIE_TAGS MT, MOVIE_DIRECTORS MD\nWHERE MD.MOVIEID = MT.MOVIEID AND T.TAGID = MT.TAGID AND MD.DIRECTORNAME = '"+ directorList.get(i)+"' AND MT.MOVIEID IN("+movieA+") \n";
        movieD += "SELECT DISTINCT D.MOVIEID\nFROM MOVIE_DIRECTORS D\nWHERE D.DIRECTORNAME='"+directorList.get(i)+"' AND D.MOVIEID IN("+movieA+")"+"\n";

        ResultSet rS = null;

        try
        {
            prepStatement = con.prepareStatement(tags);
            rS =prepStatement.executeQuery(tags);
            tagModel.clear();
            if(rS.next() == false) {
                tagModel.addElement("No tags found for the given criteria");

            }
            rS =prepStatement.executeQuery(tags);
            while(rS.next())
            {
                tagModel.addElement(rS.getString("TAGID")+" "+rS.getString("VALUE"));
            }
            prepStatement.close();
            rS.close();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        tagJList.setModel(tagModel);

        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                movieS="";
                movieT="";
                movieModel.clear();
                userModel.clear();
                textArea.setText("");
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                tagList.clear();
                for (int i = 0; i< selections.length; i++) {
                    tagList.add(selectionValues[i].toString());
                }
            }
        };


        tagJList.addListSelectionListener(selectionListener);

    }

    private void populateMovieSearch(ActionEvent evt) {
        textArea.setText("");
        finalMovieList = "";
        movieS="";
        movieModel.clear();
        userModel.clear();
        String bAttr = "";
        String val = "";

        if (comboBox.getSelectedIndex() == 1) {
            bAttr = "INTERSECT";
        } else {
            if (comboBox.getSelectedIndex() == 0 || comboBox.getSelectedIndex() == 2) {
                bAttr = "UNION";
            }
        }

        start = String.valueOf(jYearChooser1.getYear());
        stop = String.valueOf(jYearChooser2.getYear());

        if (movie == "" && genreList.isEmpty()) {
            //do nothing
        } else if (movie == "" && !genreList.isEmpty() && start != null && stop != null) {
            //When User selected genre and years; but did not click on populate country
            int i;
            for (i = 0; i < genreList.size() - 1; i++) {
                movieS += "SELECT DISTINCT M.MOVIEID \nFROM MOVIE_GENRES MG, MOVIES M\nWHERE M.MOVIEID=MG.MOVIEID AND M.MYEAR >=" + start + " AND M.MYEAR <=" + stop + " AND MG.GENRE = '" + genreList.get(i) + "'\n" + bAttr + "\n";
            }
            movieS += "SELECT DISTINCT M.MOVIEID\nFROM MOVIE_GENRES MG, MOVIES M\nWHERE M.MOVIEID=MG.MOVIEID AND M.MYEAR >=" + start + " AND M.MYEAR <=" + stop + " AND MG.GENRE = '" + genreList.get(i) + "'";

            //movieS = movie;
        } else if (movieC == "" && countryList.isEmpty()) {
            //Show movies from genre for given years when Countries are populated but not selected by user
            movieS = movie;
        } else if (movieC == "" && !countryList.isEmpty()) {
            //Show movies from genre for given years for selected countries but actors are not populated
            int i;
            for (i = 0; i < countryList.size() - 1; i++) {
                movieS += "SELECT DISTINCT MC.MOVIEID\nFROM MOVIE_COUNTRIES MC\nWHERE MC.COUNTRY='" + countryList.get(i) + "' AND MC.MOVIEID IN(" + movie + ")" + "\n" + bAttr + "\n";

            }
            movieS += "SELECT DISTINCT MC.MOVIEID\nFROM MOVIE_COUNTRIES MC\nWHERE MC.COUNTRY='" + countryList.get(i) + "' AND MC.MOVIEID IN(" + movie + ")" + "\n";
            //movieS = movieC;
        } else if (movieA == "" && actorList.isEmpty()) {
            //Show movies from genre for given years for selected countries but Actors are ot selected
            movieS = movieC;
        } else if (movieA == "" && !actorList.isEmpty()) {
            //Show movies from genre for given years for selected countries and selected actors
            int i;
            for(i=0;i<actorList.size()-1;i++)
            {
                movieS += "SELECT DISTINCT A.MOVIEID\nFROM MOVIE_ACTORS A\nWHERE A.ACTORNAME='"+actorList.get(i)+"' AND A.MOVIEID IN("+movieC+")"+"\n"+bAttr+"\n";
            }
            movieS += "SELECT DISTINCT A.MOVIEID\nFROM MOVIE_ACTORS A\nWHERE A.ACTORNAME='"+actorList.get(i)+"' AND A.MOVIEID IN("+movieC+")"+"\n";

            //movieS = movieA;
        } else if (movieD == "" && directorList.isEmpty()) {
            //Show movies from genre for given years for selected countries & Actors and populate Director is called, but not selected
            movieS = movieA;
        } else if (movieD == "" && !directorList.isEmpty()) {
            //Show movies from genre for given years for selected countries & Actors & Directors
            int i;
            for(i=0;i<directorList.size()-1;i++)
            {
                movieS += "SELECT DISTINCT D.MOVIEID\nFROM MOVIE_DIRECTORS D\nWHERE D.DIRECTORNAME='"+directorList.get(i)+"' AND D.MOVIEID IN("+movieA+")"+"\n"+bAttr+"\n";
            }
            movieS += "SELECT DISTINCT D.MOVIEID\nFROM MOVIE_DIRECTORS D\nWHERE D.DIRECTORNAME='"+directorList.get(i)+"' AND D.MOVIEID IN("+movieA+")"+"\n";
            //movieS = movieD;
        } else if (movieD!="" && tagList.isEmpty()) {
            //When user had populated the Tags but not selected any tags
            movieS = movieD;
        } else if(!tagList.isEmpty()){
            //When the user has selected everything including the Tags

            if (comboBox_6.getSelectedIndex() == 1) {
                val = ">";
            } else {
                if (comboBox_6.getSelectedIndex() == 2) {
                    val = "<";
                }
                if (comboBox_6.getSelectedIndex() == 0) {
                    val = "=";
                }

            }

            //Genre Within attributes
            int i = 0;

            String tagId;
            for (i = 0; i < tagList.size() - 1; i++) {
                tagId = tagList.get(i).substring(0, tagList.get(i).indexOf(" "));
                System.out.println("tag id is: " + tagId);
                movieT += "SELECT DISTINCT MT.MOVIEID\nFROM MOVIE_TAGS MT \nWHERE MT.TAGID='" + tagId + "' AND ";
                if(value != null) {
                    movieT += "MT.TAGWEIGHT " + val + " " + value + " AND ";
                }
                movieT += "MT.MOVIEID IN (" + movieD + ") \n " + bAttr + "\n ";
            }

            tagId = tagList.get(i).substring(0, tagList.get(i).indexOf(" "));
            System.out.println("tag id is: " + tagId);
            movieT += "SELECT DISTINCT MT.MOVIEID\nFROM MOVIE_TAGS MT \nWHERE MT.TAGID='" + tagId + "' AND ";
            if(value != null) {
                movieT += "MT.TAGWEIGHT " + val + " " + value + " AND ";
            }
            movieT += "MT.MOVIEID IN (" + movieD + ") \n ";
            movieS = movieT;
        }

        //Get the Genre list to filter the final result based on movieId and Genres
        StringBuilder s = new StringBuilder();
        int i;
        for(i=0; i<genreList.size()-1; i++) {
            s.append("'" + genreList.get(i) + "'");
            s.append(",");
        }
        s.append("'" + genreList.get(i) + "'");

        System.out.println("the genre list is: " + s);
        finalMovieList =  "SELECT DISTINCT M.MOVIEID, M.TITLE, MG.GENRE, M.MYEAR, MC.COUNTRY, M.RTAUDIENCERATING, M.RTAUDIENCENUMRATINGS FROM MOVIES M, MOVIE_GENRES MG, MOVIE_COUNTRIES MC WHERE M.MOVIEID = MG.MOVIEID AND M.MOVIEID = MC.MOVIEID AND MG.GENRE IN(" + s + ") AND M.MOVIEID IN (" + movieS + ")";
        ResultSet rS = null;

        try
        {
            prepStatement = con.prepareStatement(finalMovieList);
            rS =prepStatement.executeQuery(finalMovieList);

            if(rS.next() == false) {
                movieModel.addElement("No movies found for the given criteria");

            }
            rS =prepStatement.executeQuery(finalMovieList);
            while(rS.next())
            {
                movieModel.addElement(rS.getString("MOVIEID") + "," + rS.getString("TITLE") + "," + rS.getString("GENRE") + "," + rS.getString("MYEAR") + "," + rS.getString("COUNTRY") + "," + rS.getString("RTAUDIENCERATING") + "," + rS.getString("RTAUDIENCENUMRATINGS"));
            }
            prepStatement.close();
            rS.close();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        movieQJList.setModel(movieModel);
        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                user="";
                userModel.clear();
                textArea.setText("");
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                movieList.clear();
                for (int i = 0; i< selections.length; i++) {
                    movieList.add(selectionValues[i].toString());
                }
            }
        };

        movieQJList.addListSelectionListener(selectionListener);

    }

    private void populateUserSearch(ActionEvent evt) {
        userModel.clear();
        textArea.setText("");
        String bAttr = "";

        if(comboBox.getSelectedIndex()==1)
        {
            bAttr = "INTERSECT";
        }
        else
        {
            if(comboBox.getSelectedIndex()==0 || comboBox.getSelectedIndex()==2)
            {
                bAttr = "UNION";
            }
        }
        System.out.println("The number of movies selected are: " + movieList.size());

        //Genre Within attributes
        int i=0;

        for(i=0;i<movieList.size()-1;i++)
        {
            user += "SELECT DISTINCT UTM.USERID FROM USER_TAGGEDMOVIES UTM WHERE UTM.MOVIEID =" + movieList.get(i).split(",")[0] +" \n" + bAttr + "\n";
        }

        user += "SELECT DISTINCT UTM.USERID FROM USER_TAGGEDMOVIES UTM WHERE UTM.MOVIEID =" + movieList.get(i).split(",")[0] +" \n";

        System.out.println(user);
        ResultSet rS = null;

        try
        {
            prepStatement = con.prepareStatement(user);
            rS =prepStatement.executeQuery(user);

            if(rS.next() == false) {
                userModel.addElement("No users found for the given criteria");

            }
            rS =prepStatement.executeQuery(user);
            while(rS.next())
            {
                userModel.addElement(rS.getString("USERID"));
            }
            prepStatement.close();
            rS.close();

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        userQJList.setModel(userModel);

        MouseListener mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 1)
                {
                    userList = (ArrayList<String>) userQJList.getSelectedValuesList();
                }
            }
        };
        userQJList.addMouseListener(mouseListener);
    }

}
