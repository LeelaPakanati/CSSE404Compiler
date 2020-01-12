ReservedWord, class
ID, Factorial
Delimiter, {
ReservedWord, public
ReservedWord, static
ReservedWord, void
ReservedWord, main
Delimiter, (
ReservedWord, String
Delimiter, [
Delimiter, ]
ID, a
Delimiter, )
Delimiter, {
ReservedWord, System.out.println
Delimiter, (
ReservedWord, new
ID, Fac
Delimiter, (
Delimiter, )
Delimiter, .
ID, ComputeFac
Delimiter, (
Integer, 10
Delimiter, )
Delimiter, )
Delimiter, ;
Delimiter, }
Delimiter, }
ReservedWord, class
ID, Fac
Delimiter, {
ReservedWord, public
ReservedWord, int
ID, ComputeFac
Delimiter, (
ReservedWord, int
ID, num
Delimiter, )
Delimiter, {
ReservedWord, int
ID, num_aux
Delimiter, ;
ReservedWord, if
Delimiter, (
ID, num
Operator, <
Integer, 1
Delimiter, )
ID, num_aux
Delimiter, =
Integer, 1
Delimiter, ;
ReservedWord, else
ID, num_aux
Delimiter, =
ID, num
Operator, *
Delimiter, (
ID, this
Delimiter, .
ID, ComputeFac
Delimiter, (
ID, num
Operator, -
Integer, 1
Delimiter, )
Delimiter, )
Delimiter, ;
ReservedWord, return
ID, num_aux
Delimiter, ;
Delimiter, }
Delimiter, }
eof, $
Tree: 
 Program
  MainClassDecl
   class
   ID
   {
   public
   static
   void
   main
   (
   String
   [
   ]
   ID
   )
   {
   StmtList
    Stmt
     AtomicStmt
      System.out.println
      (
      Expr
       OrOperand
        AndOperand
         EqualityOperand
          RelOperand
           AddOperand
            Value
             new
             NewWhat
              ID
              (
              )
              ValuePrime
               .
               DotWhat
                ID
                (
                ExprList
                 Expr
                  OrOperand
                   AndOperand
                    EqualityOperand
                     RelOperand
                      AddOperand
                       Value
                        Integer
                       AddOperandPrime
                      RelOperandPrime
                     EqualityOperandPrime
                    AndOperandPrime
                   OrOperandPrime
                  ExprPrime
                 ExprListPrime
                )
                ValuePrime
            AddOperandPrime
           RelOperandPrime
          EqualityOperandPrime
         AndOperandPrime
        OrOperandPrime
       ExprPrime
      )
      ;
    StmtList
   }
   }
  ClassDeclList
   ClassDecl
    class
    ID
    ClassDeclPrime
    {
    ClassVarDeclList
    MethodDeclList
     MethodDecl
      public
      Formal
       Type
        int
        IsArr
       ID
      (
      FormalList
       Formal
        Type
         int
         IsArr
        ID
       FormalListPrime
      )
      {
      StmtList
       Stmt
        AtomicStmt
         int
         IsArr
         ID
         IsArr
         Assignment
          ;
       StmtList
        Stmt
         if
         (
         Expr
          OrOperand
           AndOperand
            EqualityOperand
             RelOperand
              AddOperand
               Value
                ID
                ValuePrime
               AddOperandPrime
              RelOperandPrime
             EqualityOperandPrime
              <
              RelOperand
               AddOperand
                Value
                 Integer
                AddOperandPrime
               RelOperandPrime
              EqualityOperandPrime
            AndOperandPrime
           OrOperandPrime
          ExprPrime
         )
         BracketStmt
          Stmt
           AtomicStmt
            ID
            IsArr
            IDAssignment
             =
             Expr
              OrOperand
               AndOperand
                EqualityOperand
                 RelOperand
                  AddOperand
                   Value
                    Integer
                   AddOperandPrime
                  RelOperandPrime
                 EqualityOperandPrime
                AndOperandPrime
               OrOperandPrime
              ExprPrime
             ;
         else
         BracketStmt
          Stmt
           AtomicStmt
            ID
            IsArr
            IDAssignment
             =
             Expr
              OrOperand
               AndOperand
                EqualityOperand
                 RelOperand
                  AddOperand
                   Value
                    ID
                    ValuePrime
                   AddOperandPrime
                    *
                    Value
                     (
                     Expr
                      OrOperand
                       AndOperand
                        EqualityOperand
                         RelOperand
                          AddOperand
                           Value
                            ID
                            ValuePrime
                             .
                             DotWhat
                              ID
                              (
                              ExprList
                               Expr
                                OrOperand
                                 AndOperand
                                  EqualityOperand
                                   RelOperand
                                    AddOperand
                                     Value
                                      ID
                                      ValuePrime
                                     AddOperandPrime
                                    RelOperandPrime
                                     -
                                     AddOperand
                                      Value
                                       Integer
                                      AddOperandPrime
                                     RelOperandPrime
                                   EqualityOperandPrime
                                  AndOperandPrime
                                 OrOperandPrime
                                ExprPrime
                               ExprListPrime
                              )
                              ValuePrime
                           AddOperandPrime
                          RelOperandPrime
                         EqualityOperandPrime
                        AndOperandPrime
                       OrOperandPrime
                      ExprPrime
                     )
                     ValuePrime
                    AddOperandPrime
                  RelOperandPrime
                 EqualityOperandPrime
                AndOperandPrime
               OrOperandPrime
              ExprPrime
             ;
        StmtList
      return
      Expr
       OrOperand
        AndOperand
         EqualityOperand
          RelOperand
           AddOperand
            Value
             ID
             ValuePrime
            AddOperandPrime
           RelOperandPrime
          EqualityOperandPrime
         AndOperandPrime
        OrOperandPrime
       ExprPrime
      ;
      }
     MethodDeclList
    }
   ClassDeclList

