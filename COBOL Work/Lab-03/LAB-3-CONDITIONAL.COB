      ******************************************************************
      * Author: Jake Elliott
      * Date: 6/17/2025
      * Purpose: TO ALLOW USERS TO COLLECT USER INPUT FOR TICKETS CHOICE
      * AND SUM THE PRICES TO DISPLAY THE TOTAL COST OF TICKETS INPUT.
      * USING CONDITIONALS INSTEAD OF LEVEL 88'S.
      * Tectonics: cobc
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. LAB-3-CONDITIONAL.
       DATA DIVISION.
       FILE SECTION.
       WORKING-STORAGE SECTION.
       01 USER-TYPE PIC X(10).
       01 PRICE PIC 9(3)V99 VALUE 0.
       01 TOTAL PIC 9(3)V99 VALUE 0.

       PROCEDURE DIVISION.
       100-MAIN-PROCEDURE.
           PERFORM 200-GATHER-INPUT
           PERFORM 303-DISPLAY-TOTAL-PRICE
           STOP RUN.

       200-GATHER-INPUT.
           PERFORM UNTIL USER-TYPE = "QUIT"
               PERFORM 304-DISPLAY-TICKET-TYPE
               PERFORM 201-READ-INPUT
               IF USER-TYPE NOT = "QUIT"
                   PERFORM 300-CALCULATE-TOTAL
                   PERFORM 301-DISPLAY-PRICE
                   PERFORM 302-SUM-TOTAL
               END-IF
           END-PERFORM.

       201-READ-INPUT.
           DISPLAY "ENTER TICKET TYPE: " WITH NO ADVANCING
           ACCEPT USER-TYPE.

       300-CALCULATE-TOTAL.
           IF USER-TYPE = "FAMILY"
           MOVE 80 TO PRICE
           ELSE
               IF USER-TYPE = "ADULT"
               MOVE 25 TO PRICE
               ELSE
                   IF USER-TYPE = "STUDENT"
                   MOVE 20 TO PRICE
                   ELSE
                       IF USER-TYPE = "YOUTH"
                       MOVE 16 TO PRICE
                       ELSE
                           IF USER-TYPE = "CHILD"
                           MOVE 0 TO PRICE
                           ELSE
                               IF USER-TYPE = "MILITARY"
                               MOVE 0 TO PRICE
                               ELSE DISPLAY "INVALID ENTRY"
                               MOVE 0 TO PRICE
                               END-IF
                           END-IF
                       END-IF
                   END-IF
               END-IF
           END-IF.


       301-DISPLAY-PRICE.
           DISPLAY "TICKET PRICE: $" PRICE.

       302-SUM-TOTAL.
           ADD PRICE TO TOTAL.

       303-DISPLAY-TOTAL-PRICE.
           DISPLAY "TOTAL PRICE: $" TOTAL.

       304-DISPLAY-TICKET-TYPE.
           DISPLAY "TICKET TYPES AVAILABLE: "
           "(FAMILY, ADULT, STUDENT, YOUTH, CHILD, MILITARY OR QUIT): ".

       END PROGRAM LAB-3-CONDITIONAL.
