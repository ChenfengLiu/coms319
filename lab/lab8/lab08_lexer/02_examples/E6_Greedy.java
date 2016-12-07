// Generated from E6_Greedy.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class E6_Greedy extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT1=1, WS=2;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"COMMENT1", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "COMMENT1", "WS"
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


	public E6_Greedy(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "E6_Greedy.g4"; }

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
		case 0:
			COMMENT1_action((RuleContext)_localctx, actionIndex);
			break;
		case 1:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COMMENT1_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			System.out.println("comment1: "+getText());
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			skip();
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\4\34\b\1\4\2\t\2"+
		"\4\3\t\3\3\2\3\2\3\2\3\2\7\2\f\n\2\f\2\16\2\17\13\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\3\6\3\27\n\3\r\3\16\3\30\3\3\3\3\2\2\4\3\3\5\4\3\2\3\5\2\13\f\17"+
		"\17\"\"\35\2\3\3\2\2\2\2\5\3\2\2\2\3\7\3\2\2\2\5\26\3\2\2\2\7\b\7\61\2"+
		"\2\b\t\7,\2\2\t\r\3\2\2\2\n\f\13\2\2\2\13\n\3\2\2\2\f\17\3\2\2\2\r\13"+
		"\3\2\2\2\r\16\3\2\2\2\16\20\3\2\2\2\17\r\3\2\2\2\20\21\7,\2\2\21\22\7"+
		"\61\2\2\22\23\3\2\2\2\23\24\b\2\2\2\24\4\3\2\2\2\25\27\t\2\2\2\26\25\3"+
		"\2\2\2\27\30\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\32\3\2\2\2\32\33\b"+
		"\3\3\2\33\6\3\2\2\2\5\2\r\30\4\3\2\2\3\3\3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}