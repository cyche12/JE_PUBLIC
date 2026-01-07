      ******************************************************************
      * Author: Jake Elliott
      * Date: 6/17/2025
      * Purpose: TO ALLOW USERS TO COLLECT USER INPUT FOR TICKETS CHOICE
      * AND SUM THE PRICES TO DISPLAY THE TOTAL COST OF TICKETS INPUT.
      * Tectonics: cobc
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. lab-3.
       DATA DIVISION.
       FILE SECTION.
       WORKING-STORAGE SECTION.
       01 USER-TYPE PIC X(10).
           88 FAMILY-TYPE VALUE "FAMILY".
           88 ADULT-TYPE VALUE "ADULT".
           88 STUDENT-TYPE VALUE "STUDENT".
           88 YOUTH-TYPE VALUE "YOUTH".
           88 CHILD-TYPE VALUE "CHILD".
           88 MILITARY-TYPE VALUE "MILITARY".
           88 QUIT-TYPE VALUE "QUIT".
       01 PRICE PIC 9(3)V99 VALUE 0.
       01 TOTAL PIC 9(3)V99 VALUE 0.

       PROCEDURE DIVISION.
       100-MAIN-PROCEDURE.
           PERFORM 200-GATHER-INPUT
           PERFORM 303-DISPLAY-TOTAL-PRICE
           STOP RUN.

       200-GATHER-INPUT.
           PERFORM UNTIL QUIT-TYPE
               PERFORM 304-DISPLAY-TICKET-TYPE
               PERFORM 201-READ-INPUT
               IF NOT QUIT-TYPE
                   PERFORM 300-CALCULATE-TOTAL
                   PERFORM 301-DISPLAY-PRICE
                   PERFORM 302-SUM-TOTAL
               END-IF
           END-PERFORM.

       201-READ-INPUT.
           DISPLAY "ENTER TICKET TYPE: " WITH NO ADVANCING
           ACCEPT USER-TYPE.

       300-CALCULATE-TOTAL.
       IF FAMILY-TYPE MOVE 80 TO PRICE
           ELSE IF ADULT-TYPE MOVE 25 TO PRICE
               ELSE IF STUDENT-TYPE MOVE 20 TO PRICE
                   ELSE IF YOUTH-TYPE MOVE 16 TO PRICE
                       ELSE IF CHILD-TYPE MOVE 0 TO PRICE
                           ELSE IF MILITARY-TYPE MOVE 0 TO PRICE
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

       END PROGRAM lab-3.
