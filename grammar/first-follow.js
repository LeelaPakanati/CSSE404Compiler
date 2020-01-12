var Grammar = require('first-follow').Grammar;

// S -> abA
// A -> bc
// A -> ε
var grammar = new Grammar([
  { left: 'Program', right: ['MainClassDecl', 'ClassDecl'] },

  { left: 'MainClassDecl', right: ['class', 'ID', '{', 'public', 'static', 'void', 'main', '(', 'String', '[', ']', 'ID', ')', '{', 'StmtList', '}', '}'] },

  { left: 'ClassDeclList', right: ['ClassDecl', 'ClassDeclList'] },
  { left: 'ClassDeclList', right: [null] },

  { left: 'ClassDecl', right: ['class', 'ID', '[', 'extends', 'ID', '{', 'ClassVarDeclList', 'MethodDeclList', '}'] },

  { left: 'ClassVarList', right: ['ClassVar', 'ClassVarList'] },
  { left: 'ClassVarList', right: [null] },

  { left: 'ClassVarDecl', right: ['Type', 'ID'] },

  { left: 'MethodDeclList', right: ['MethodDecl', 'MethodDeclList'] },
  { left: 'MethodDeclList', right: [null] },

  { left: 'MethodDecl', right: ['public', 'Type', 'ID', '(', '[', 'Formal', 'FormalList', ']', ')', '{', 'StmtList', 'return', 'Expr', ';', '}'] },

  { left: 'FormalList', right: [',', 'Formal', 'FormalList'] },
  { left: 'FormalList', right: [null] },

  { left: 'Formal', right: ['Type', 'ID'] },

  { left: 'Type', right: ['int', '[', ']'] },
  { left: 'Type', right: ['AtomicType']},

  { left: 'AtomicType', right: ['int'] },
  { left: 'AtomicType', right: ['boolean'] },
  { left: 'AtomicType', right: ['ID'] },
 
  { left: 'StmtList', right: ['Stmt', 'StmtList'] },
  { left: 'StmtList', right: [null] },

  { left: 'Stmt', right: ['if', '(', 'Expr', ')', 'BracketStmt', 'else', 'BracketStmt'] },
  { left: 'Stmt', right: ['while', '(', 'Expr', ')', 'BracketStmt'] },
  { left: 'Stmt', right: ['AtomicStmt'] },

  { left: 'BracketStmt', right: ['{', 'AtomicStmtList', '}'] },
  { left: 'BracketStmt', right: ['AtomicStmt'] },

  { left: 'AtomicStmtList', right: ['AtomicStmt', 'AtomicStmtList'] },
  { left: 'AtomicStmtList', right: [null] },

  { left: 'AtomicStmt', right: ['Type', 'ID', '=', 'Expr', ';'] },
  { left: 'AtomicStmt', right: ['System.out.println', '(', 'Expr', ')', ';'] },
  { left: 'AtomicStmt', right: ['ID', '=', 'Expr'] },
  { left: 'AtomicStmt', right: ['ID', '[', 'Expr', ']', '=', 'Expr'] },

  { left: 'ExprList', right: [',', 'Expr', 'ExprList'] },
  { left: 'ExprList', right: [null] },

  { left: 'Expr', right: ['OrOperand', 'ExprP'] },

  { left: 'ExprP', right: ['||', 'OrOperand', 'ExprP'] },
  { left: 'ExprP', right: [null] },

  { left: 'OrOperand', right: ['AndOperand', 'OrOperandP'] },

  { left: 'OrOperandP', right: ['&&', 'AndOperand', 'OrOperandP'] },
  { left: 'OrOperandP', right: [null] },

  { left: 'AndOperand', right: ['EqualityOperand', 'AndOperandP',] },

  { left: 'AndOperandP', right: ['==', 'EqualityOperand', 'AndOperandP'] },
  { left: 'AndOperandP', right: ['!=', 'EqualityOperand', 'AndOperandP'] },
  { left: 'AndOperandP', right: [null] },

  { left: 'RelOperand', right: ['AddOperand', 'RelOperandP',] },

  { left: 'RelOperandP', right: ['+', 'AddOperand', 'RelOperandP'] },
  { left: 'RelOperandP', right: ['-', 'AddOperand', 'RelOperandP'] },
  { left: 'RelOperandP', right: [null] },

  { left: 'AddOperand', right: ['MulOperand', 'AddOperandP',] },

  { left: 'AddOperandP', right: ['*', 'MulOperand', 'AddOperandP'] },
  { left: 'AddOperandP', right: ['/', 'MulOperand', 'AddOperandP'] },
  { left: 'AddOperandP', right: [null] },

  { left: 'UnaryOperand', right: ['Expr', '[', 'Expr', ']'] },
  { left: 'UnaryOperand', right: ['AtomicUnaryOperand'] },

  { left: 'AtomicUnaryOperand', right: ['(', 'ParenOperand', ')'] },
  { left: 'AtomicUnaryOperand', right: ['ParenOperand'] },

  { left: 'ParenOperand', right: ['Value', '.', 'length']},
  { left: 'ParenOperand', right: ['AtomicParenOperand'] },

  { left: 'AtomicParenOperand', right: ['Value', '.', 'ID', '(', '[', 'Expr', 'ExprList', ']', ')'] },
  { left: 'AtomicParenOperand', right: ['Value'] },

  { left: 'Value', right: ['new', 'int', '[', ']'] },
  { left: 'Value', right: ['AtomicValue'] },

  { left: 'AtomicValue', right: ['new', 'ID', '(', ')'] },
  { left: 'AtomicValue', right: ['ID'] },
  { left: 'AtomicValue', right: ['this'] },
  { left: 'AtomicValue', right: ['Integer'] },
  { left: 'AtomicValue', right: ['null'] },
  { left: 'AtomicValue', right: ['true'] },
  { left: 'AtomicValue', right: ['false'] }
  
]);

console.log(grammar.getFirstSets());
console.log(grammar.getFollowSets());
console.log(grammar.getPredictSets());
