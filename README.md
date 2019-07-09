# IMDB-Movie-Data-Analysis-Application

The dataset used for the project is an extension of MovieLens10M dataset, published by GroupLeans research group, http://www.grouplens.org. The datadset links the movies of MovieLens dataset with their corresponding web pages at Internet Movie Database (IMDb) and Rotten Tomatoes movie review systems.
http://www.imdb.com
http://www.rottentomatoes.com
The dataset includes 2113 users, 10197 movies, 855598 ratings and 13222 tags.

Overview & Requirements:
You would develop a target application which runs queries on the MovieLens/imdb data and extracts useful information. The primary users for this application will be users seeking for movies and their ratings that match their search criteria. Your application will have a user interface that supports two types of search: 1) movie search, and 2) user search. In the first step, movie attributes such as genre, year, country, cast, and user’s tags (e.g. tags that viewers assigned to movies) can be selected to search for movies. Using this application, the user will search for the movies from various categories that have the properties (attributes) the user is looking for. In the second step, movie ids from the results of previous step in addition to user’s tags can be used to search for users/viewers. The result of this search will show users that assigned selected tags to the selected movies.

Faceted search has become a popular technique in commercial search applications, particularly for online retailers and libraries. It is a technique for accessing information organized according to a faceted classification system, allowing users to explore a collection of information by applying multiple filters. Faceted search is the dynamic clustering of items or search results into categories that let users drill into search results (or even skip searching entirely) by any value in any field. Users can then “drill down” by applying specific constraints to the search results. Look at https://react.rocks/tag/Faceted_Search for some examples.

In this application, the user can filter the search results using available movie attributes (i.e. facets) such as genre, year, country, casts, and movie tags. Each time the user clicks on a facet value; the set of results is reduced to only the items that have that value. Additional clicks continue to narrow down the search—the previous facet values are remembered and applied again.
You will be designing your application as a standalone Java application.

Example screenshots of a possible application GUI are available in Appendix-B. In evaluating your work, instructor’s primary focus will be primarily on how you design your database and how efficiently you can search the database and pull out the information. However, your GUI should provide the basic functionality for easy browsing of the movie categories and attributes (as illustrated in Appendix-B). Creativity is encouraged!


Project Details:

0. Part 0
- Install Oracle Database 11gR2 or later. Consult the instructions provided on Camino under Assignment 3. If you are using a MAC laptop, you can install a virtualization software such as Virtual Box, and install a Windows or Linux guest operating system. You can then install Oracle Database on this environment.

I. Part 1
- Download the MovieLens dataset from Camino. Look at each data file and understand what information the data objects provide. Pay attention to the data items in data objects that you will need for your application (For example, movie attributes, etc.)
- You may have to modify your database design from Homework 2 to model the database for the described application scenario on page-1. Your database schema doesn’t necessarily need to include all the data items provided in the data files. Your schema should be precise but yet complete. It should be designed in such a way that all queries/data retrievals on/from the database run efficiently and effectively.
- Produce DDL SQL statements for creating the corresponding tables in a relational DBMS. Note the constraints, including key constraints, referential integrity constraints, not NULL constraints, etc. needed for the relational schema to capture and enforce the semantics of your ER design.
- Populate your database with the dataset. Generate INSERT statements for your tables and run those to insert data into your DB.
- After you populated your database, created indexes on frequently accessed columns of its tables using CREATE INDEX statement. This will help speed up query execution times. You have some flexibility about which indexes to choose.

II. Part 2
Implement the application for searching movies as explained in section “Overview & Requirements”. In this milestone you would:
• Write the SQL queries to search your database.
• Establish connectivity with the DBMS.
• Embed/execute queries in/from the code. Retrieve query results and parse the returned results to generate the output that will be displayed on the GUI.
• Movie Search: Implement a GUI where the user can search for movies that match the criteria given.
o Browse through attributes for the movies (See Appendix C); select the movie attributes that user wants to search for;

o The usage flow of the GUI is as follows:
1) Once the application is loaded, Genres attribute values are loaded from the backend database. Also movie production year selection is initialized.

2) The user is required to select both desired genres attribute values, and an interval for movie production year. Note that both of these two categories need to be selected at this step. To make the usage flow more clear, an example selection is provided at each step. For instance, assume that use selects Drama as the genres attribute value and selects year value from 2010 to 2017.

3) The Countries matching the genres and year selections will be listed under the Country attribute panel. Since user selected Drama and 2010-2017 in previous step, only country values that their movie genre is Drama and produced between 2010 and 2017 should appear in the Country attribute panel. Note how faceted search work here. After step 2, the set of results is reduced to only the movies that belong to Drama genre and are produced 2010-2017. The user can select desired Country attribute values. This attribute is optional in building the query. User might not select a country at all. Assume that use selects USA as the country attribute value.

4) Movies’ casts (actor/actress/director) are the next selection. Cast members’ names can either be entered directly into a text box, or optionally can be searched from the list of available actors, actresses, and directors by clicking on the search icon next to the text box.
This attribute is also optional in building the query. Since user selected Drama, and 2010-2017, and USA in previous steps, only cast members that appeared in a movie produced by USA AND between 2010 to 2017 AND movie genre is Drama, should appear in the cast selection panel. Assume that user selects Tom Hanks as the actor.

5) The movie tag values corresponding to the previous selections will be listed in the Movie Tag panel. This attribute is also optional in building the query. Based on previous selections, tag values corresponding to movies that are USA production AND between 2010 to 2017 AND Drama genre AND Tom Hanks played in them, should appear in the Movie Tag panel. This panel shows both tag id and tag value.

o The application should be able to search for the movies that have either all the specified attribute values (AND condition) or that have any of the attribute values specified (OR condition).
For example, if user selected AND condition, and selected Drama and Family as genre, movies with Drama AND Family genres should be listed.

If user selected OR condition, and selected Drama and Family as genre, movies with Drama OR Family genre should be listed.
Please note that the relation between facets (or movie attributes) is always AND. However, the relation between values of one facet can be selected as OR or AND.

o Select a certain movie in the search results and list the following for that movie(s): movie id, movie title, genre, year, country, average of Rotten tomato audience rating, and Rotten Tomato Audience number of ratings.

• User Search: Implement a GUI where the user can utilize movie results from previous search (Movie Search), and search for users that match the criteria given

o The usage flow of the GUI is as follows:
1) Once the movie results are shown as a result of executing movie query, user can select movie ids from movie results panel. (As shown in Figure 2)
2) Clicking on “Execute User Query” will show user ids that assigned the selected tags to selected movies.
Please note that all data displayed on the GUI should be kept in the database and should be retrieved from it when needed. You are not allowed to create internal data structures to store data.
