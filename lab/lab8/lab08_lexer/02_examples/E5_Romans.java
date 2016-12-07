// Generated from E5_Romans.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class E5_Romans extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ROMAN=1, WS=2, NEWLINE=3;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"ONE", "FOUR", "FIVE", "NINE", "TEN", "FORTY", "FIFTY", "ROMAN_BASE", 
		"ROMAN", "WS", "NEWLINE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ROMAN", "WS", "NEWLINE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	  int total = 0;


	public E5_Romans(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "E5_Romans.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 8:
			ROMAN_action((RuleContext)_localctx, actionIndex);
			break;
		case 10:
			NEWLINE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ROMAN_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			  
			          if (getText().equals("I")) total += 1;
			          else if (getText().equals("IV")) total += 4;
			          else if (getText().equals("V")) total += 5;
			          else if (getText().equals("IX")) total += 9;
			          else if (getText().equals("X")) total += 10;
			          else if (getText().equals("XL")) total += 40;
			          else if (getText().equals("L")) total += 50;
			          System.out.print("["+getText()+"]");
			       
			break;
		}
	}
	private void NEWLINE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:

			      System.out.println("\n" + total);
			      total = 0;
			    
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\5>\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\62\n\t\3\n\3\n\3\n\3\13\6\13"+
		"8\n\13\r\13\16\139\3\f\3\f\3\f\2\2\r\3\2\5\2\7\2\t\2\13\2\r\2\17\2\21"+
		"\2\23\3\25\4\27\5\3\2\3\4\2\13\13\"\"<\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\3\31\3\2\2\2\5\33\3\2\2\2\7\36\3\2\2\2\t \3\2\2\2\13#\3\2\2\2"+
		"\r%\3\2\2\2\17(\3\2\2\2\21\61\3\2\2\2\23\63\3\2\2\2\25\67\3\2\2\2\27;"+
		"\3\2\2\2\31\32\7K\2\2\32\4\3\2\2\2\33\34\7K\2\2\34\35\7X\2\2\35\6\3\2"+
		"\2\2\36\37\7X\2\2\37\b\3\2\2\2 !\7K\2\2!\"\7Z\2\2\"\n\3\2\2\2#$\7Z\2\2"+
		"$\f\3\2\2\2%&\7Z\2\2&\'\7N\2\2\'\16\3\2\2\2()\7N\2\2)\20\3\2\2\2*\62\5"+
		"\3\2\2+\62\5\5\3\2,\62\5\7\4\2-\62\5\t\5\2.\62\5\13\6\2/\62\5\r\7\2\60"+
		"\62\5\17\b\2\61*\3\2\2\2\61+\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2\61.\3\2\2"+
		"\2\61/\3\2\2\2\61\60\3\2\2\2\62\22\3\2\2\2\63\64\5\21\t\2\64\65\b\n\2"+
		"\2\65\24\3\2\2\2\668\t\2\2\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2"+
		"\2\2:\26\3\2\2\2;<\7\f\2\2<=\b\f\3\2=\30\3\2\2\2\5\2\619\4\3\n\2\3\f\3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}