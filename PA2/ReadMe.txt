

1.) Start the rmi by "start rmiregistry <port number>". Make sure you specify the same port number in the server.

2.) Setup the Environment variable using set PA2_SERVER=localhost:<port number> same as above.

3.) Use fupload and fdownload if you dont want to resume and for faster transfer of file.

4.) Now follow the steps as given in the word doc. file. i.e.

java -cp pa2.jar Server start <portnumber> (To start the Server. Make sure to use uppercase for the first letter of the class names,i.e. "Server" and "Client")

set PA2_SERVER=localhost:<port number>

java -cp pa2.jar client upload <path_on_client> </path/filename/on/server> ( To upload file on the server)

java -cp pa2.jar client download </path/existing_filename/on/server> <path_on_client> ( To download file from the server)

java -cp pa2.jar client dir </path/existing_directory/on/server> ( To list the files and folders in a directory.)

java -cp pa2.jar client mkdir </path/new_directory/on/server> ( To create a directory )

java -cp pa2.jar client rmdir </path/existing_directory/on/server> ( To remove an existing directory)

java -cp pa2.jar client rm </path/existing_filename/on/server> (To remove an existing file.)

java -cp pa2.jar client shutdown (To shutdown the server.)


 