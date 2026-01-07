      ******************************************************************
      *  Author:        Jake Elliott                                   *
      *  Student ID:    040732505                                      *
      *  Course:        CST8283 Section 302                            *
      *  Date:          7/10/2025                                      *
      *  Lab 4:         Stock Recommendation Program                    *
      ******************************************************************

       IDENTIFICATION DIVISION.
       PROGRAM-ID. Lab-04.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT STOCK-FILE
               ASSIGN TO "../STOCKS.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.
       FD  STOCK-FILE.
       01  STOCK-RECORD.
           05 STOCK-SYMBOL PIC X(7).
           05 STOCK-NAME PIC X(25).
           05 STOCK-CLOSING-PRICE PIC 9(4)V99.
           05 STOCK-RECOMMEND PIC 9.

       WORKING-STORAGE SECTION.
       01  EOF-FLAG PIC X VALUE "N".
           88 EOF-REACHED VALUE "Y".
           88 NOT-EOF VALUE "N".
       01  WS-COUNT PIC 99 VALUE 0.
       01  WS-USER-INPUT PIC X(11).
       01  WS-USER-RECOMM PIC 9 VALUE 0.
       01  WS-DISPLAY-PRICE PIC ZZZ9.99.
       01  WS-INDEX PIC 99 VALUE 0.
       01  WS-MATCH-COUNT PIC 99 VALUE 0.
       01  WS-MATCH-INDICES.
           05 MATCH-INDEX OCCURS 20 TIMES PIC 99.
       01  WS-MATCHED PIC X VALUE "N".
       01  STOCK-TABLE.
           05 STOCK-ENTRY OCCURS 20 TIMES.
               10 T-SYMBOL PIC X(7).
               10 T-NAME PIC X(25).
               10 T-PRICE PIC 9(4)V99.
               10 T-RECOMM PIC 9.

       PROCEDURE DIVISION.

       100-MAIN-PROCEDURE.
           PERFORM 200-INIT-STOCK-FILE
           PERFORM 201-READ-STOCK-FILE
           PERFORM 301-READ-USER-INPUT
           PERFORM 302-SEARCH-STOCKS
           PERFORM 303-DISPLAY-RESULTS
           PERFORM 400-CLOSE-PROGRAM
           STOP RUN.

       200-INIT-STOCK-FILE.
           OPEN INPUT STOCK-FILE
           DISPLAY "Loading stock data..."
           EXIT.

       201-READ-STOCK-FILE.
           PERFORM UNTIL EOF-REACHED
           READ STOCK-FILE
           AT END
           SET EOF-REACHED TO TRUE
           NOT AT END
           PERFORM 300-UPDATE-RECORD
           END-READ
           END-PERFORM
           CLOSE STOCK-FILE
           DISPLAY WS-COUNT " valid record(s) loaded."
           EXIT.

       300-UPDATE-RECORD.
           IF STOCK-RECOMMEND >= 1 AND STOCK-RECOMMEND <= 4
           IF WS-COUNT < 20
           ADD 1 TO WS-COUNT
           MOVE STOCK-SYMBOL TO T-SYMBOL(WS-COUNT)
           MOVE STOCK-NAME TO T-NAME(WS-COUNT)
           MOVE STOCK-CLOSING-PRICE TO T-PRICE(WS-COUNT)
           MOVE STOCK-RECOMMEND TO T-RECOMM(WS-COUNT)
           ELSE
           DISPLAY "Table full: ignoring " STOCK-SYMBOL
           END-IF
           ELSE
           DISPLAY "Invalid recommendation in record for " STOCK-SYMBOL
           END-IF
           EXIT.

       301-READ-USER-INPUT.
           DISPLAY "Enter recommendation (STRONG BUY, BUY, HOLD, SELL):" WITH NO ADVANCING
           ACCEPT WS-USER-INPUT
           EVALUATE WS-USER-INPUT
           WHEN "STRONG BUY" MOVE 1 TO WS-USER-RECOMM
           WHEN "BUY" MOVE 2 TO WS-USER-RECOMM
           WHEN "HOLD" MOVE 3 TO WS-USER-RECOMM
           WHEN "SELL" MOVE 4 TO WS-USER-RECOMM
           WHEN OTHER MOVE 0 TO WS-USER-RECOMM
           END-EVALUATE
           IF WS-USER-RECOMM = 0
           DISPLAY "Invalid input. Exiting program."
           STOP RUN
           END-IF
           EXIT.

       302-SEARCH-STOCKS.
           MOVE 1 TO WS-INDEX
           MOVE 0 TO WS-MATCH-COUNT
           MOVE "N" TO WS-MATCHED
           PERFORM UNTIL WS-INDEX > WS-COUNT
           IF T-RECOMM(WS-INDEX) = WS-USER-RECOMM
           ADD 1 TO WS-MATCH-COUNT
           MOVE WS-INDEX TO MATCH-INDEX(WS-MATCH-COUNT)
           MOVE "Y" TO WS-MATCHED
           END-IF
           ADD 1 TO WS-INDEX
           END-PERFORM
           EXIT.

       303-DISPLAY-RESULTS.
           DISPLAY "Stocks matching recommendation:"
           IF WS-MATCHED = "Y"
           MOVE 1 TO WS-INDEX
           PERFORM UNTIL WS-INDEX > WS-MATCH-COUNT
           MOVE T-PRICE(MATCH-INDEX(WS-INDEX)) TO WS-DISPLAY-PRICE
           DISPLAY T-NAME(MATCH-INDEX(WS-INDEX)) " - " WS-DISPLAY-PRICE
           ADD 1 TO WS-INDEX
           END-PERFORM
           ELSE
           DISPLAY "No stocks found with that recommendation."
           END-IF
           EXIT.

       400-CLOSE-PROGRAM.
           DISPLAY "Program completed."
           EXIT.

       END PROGRAM Lab-04.
