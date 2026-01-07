      ******************************************************************
      * Author:        Jake Elliott                                    *
      * Student ID:    040732505                                       *
      * Course:        CST8283 Section 302                             *
      * Lab:           LAB-5                                           *
      * Purpose:       Read unsorted STOCKS.txt and write a sorted     *
      *                indexed file with STOCK-SYMBOL as primary key   *
      ******************************************************************

       IDENTIFICATION DIVISION.
       PROGRAM-ID. LAB-5-CREATE.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.

           SELECT STOCK-INFILE ASSIGN TO "../STOCKS.txt"
           ORGANIZATION IS LINE SEQUENTIAL.

           SELECT STOCK-INDEXED ASSIGN TO "../STOCK-INDEX.DAT"
           ORGANIZATION IS INDEXED
           ACCESS MODE IS SEQUENTIAL
           RECORD KEY IS STOCK-SYMBOL
           ALTERNATE RECORD KEY IS INVESTMENT-SECTOR
           WITH DUPLICATES.

       DATA DIVISION.

       FILE SECTION.
       FD STOCK-INFILE.
       01 STOCK-IN-RECORD.
           05 IN-STOCK-SYMBOL PIC X(7).
           05 IN-STOCK-NAME PIC X(25).
           05 IN-CLOSING-PRICE-TEXT PIC X(6).
           05 IN-INVESTMENT-SECTOR PIC X(20).

       FD STOCK-INDEXED.
       01 STOCK-OUT-RECORD.
           05 STOCK-SYMBOL PIC X(7).
           05 STOCK-NAME PIC X(25).
           05 CLOSING-PRICE PIC 9(6)V99.
           05 INVESTMENT-SECTOR  PIC X(20).

       WORKING-STORAGE SECTION.
       01 WS-EOF-FLAG PIC X VALUE 'N'.
           88 END-OF-FILE VALUE 'Y'.
           88 NOT-END-OF-FILE VALUE 'N'.

       01 WS-CLOSING-PRICE            PIC 9(6)V99 VALUE 0.
       01 STOCK-COUNT PIC 9(3) VALUE 0.
       01 I PIC 9(3).
       01 J PIC 9(3).
       01 START-J PIC 9(3) VALUE 0.

       01 TEMP-SYMBOL PIC X(7).
       01 TEMP-NAME PIC X(25).
       01 TEMP-PRICE-TEXT PIC X(6).
       01 TEMP-SECTOR PIC X(20).

       01 STOCK-TABLE.
           05 STOCK-ENTRY OCCURS 100 TIMES.
               10 T-SYMBOL PIC X(7).
               10 T-NAME PIC X(25).
               10 T-PRICE-TEXT PIC X(6).
               10 T-SECTOR PIC X(20).

       PROCEDURE DIVISION.

       100-MAIN-PROCEDURE.
           PERFORM 200-OPEN-FILES
           PERFORM 300-LOAD-TABLE
           PERFORM 310-SORT-TABLE
           PERFORM 320-WRITE-FILE
           PERFORM 400-CLOSE-FILES
           STOP RUN.

       200-OPEN-FILES.
           OPEN INPUT STOCK-INFILE
           OPEN OUTPUT STOCK-INDEXED.

       300-LOAD-TABLE.
           PERFORM UNTIL END-OF-FILE
           READ STOCK-INFILE AT END
           SET END-OF-FILE TO TRUE NOT AT END ADD 1 TO STOCK-COUNT
           MOVE IN-STOCK-SYMBOL TO T-SYMBOL(STOCK-COUNT)
           MOVE IN-STOCK-NAME TO T-NAME(STOCK-COUNT)
           MOVE IN-CLOSING-PRICE-TEXT TO T-PRICE-TEXT(STOCK-COUNT)
           MOVE IN-INVESTMENT-SECTOR TO T-SECTOR(STOCK-COUNT)
           END-PERFORM.

       310-SORT-TABLE.
           PERFORM VARYING I FROM 1 BY 1 UNTIL I > STOCK-COUNT
           COMPUTE START-J = I + 1

           PERFORM VARYING J FROM START-J BY 1 UNTIL J > STOCK-COUNT
           IF T-SYMBOL(I) > T-SYMBOL(J)

           MOVE T-SYMBOL(I) TO TEMP-SYMBOL
           MOVE T-NAME(I) TO TEMP-NAME
           MOVE T-PRICE-TEXT(I) TO TEMP-PRICE-TEXT
           MOVE T-SECTOR(I) TO TEMP-SECTOR

           MOVE T-SYMBOL(J) TO T-SYMBOL(I)
           MOVE T-NAME(J) TO T-NAME(I)
           MOVE T-PRICE-TEXT(J) TO T-PRICE-TEXT(I)
           MOVE T-SECTOR(J)TO T-SECTOR(I)

           MOVE TEMP-SYMBOL TO T-SYMBOL(J)
           MOVE TEMP-NAME TO T-NAME(J)
           MOVE TEMP-PRICE-TEXT TO T-PRICE-TEXT(J)
           MOVE TEMP-SECTOR TO T-SECTOR(J)
           END-IF
           END-PERFORM
           END-PERFORM.

       320-WRITE-FILE.
           PERFORM VARYING I FROM 1 BY 1 UNTIL I > STOCK-COUNT
               DISPLAY "Writing: " T-SYMBOL(I)
               MOVE T-SYMBOL(I) TO STOCK-SYMBOL
               MOVE T-NAME(I) TO STOCK-NAME
               MOVE T-SECTOR(I) TO INVESTMENT-SECTOR
               MOVE FUNCTION NUMVAL(T-PRICE-TEXT(I)) TO WS-CLOSING-PRICE
               MOVE WS-CLOSING-PRICE TO CLOSING-PRICE
               WRITE STOCK-OUT-RECORD
           END-PERFORM.

       400-CLOSE-FILES.
           CLOSE STOCK-INFILE
           CLOSE STOCK-INDEXED.
