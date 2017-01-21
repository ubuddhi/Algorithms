__author__ = 'Udayasri'

import sys
outputfilename = sys.argv[1]  #Reads the file name
decinputfile = open(outputfilename,encoding="UTF-16") # opens the file
codes = decinputfile.read() #Reads the data from the file
output = decinputfile.name.split(".") #separates the file name based on dot operator
decoutputfilename = output[0]+"_decoded" #edits the filename with _decoded
decoutputfile = open(decoutputfilename, 'w') #opens the new file with the new file name
N=sys.argv[2] # takes the second command line arguments
MAX_TABLE_SIZE = 2**int(N) # declares the maximum table size
table = dict((i,chr(i)) for i in range(255)) # initializes the table from 0-255
code = codes.split(" ") # separates the data based on white spaces
string = table[int(code[0])] #converts the string into integer and decodes  the first code
decoutputfile.write(string) #witing the decoded output to the new file
for i in code[1:]: # loop for the remaining codes
        if int(i) not in table.keys(): #if the given code is not in the table
            new_string = string + string[0]
        else:
            new_string = table[int(i)] #decoding
        decoutputfile.write(new_string) #writing to the file
        if len(table) < MAX_TABLE_SIZE: #checking if the size of the table is withing the maximum size declared
            table[len(table)+1] = string + new_string[0] #adds the new string to the table
        string = new_string
decinputfile.close()#closing input file
decoutputfile.close() #closing output file