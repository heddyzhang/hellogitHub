package com.batch;

/**
 * メッセージ定数を扱うクラス。
 * @author chou
 *
 */
public interface MessageInfo {

	// 共通
	public final String CP5J010001 = "ドライバ登録エラー(%s)";
	public final String CP5J010002 = "DB接続に失敗しました。(SQLCODE=%d)";
	public final String CP5J010003 = "DB切断を実施しました。";
	public final String CP5J010004 = "DB切断に失敗しました。(SQLCODE=%d)";
	public final String CP5J110009 = "環境変数(%s)取得エラー";
	public final String CP5J110028 = "予期しない例外発生(%s)";
	public final String CP5J210032 = "オブジェクトのクローズに失敗しました。(SQLCODE=%d)";
	public final String CP5J110005 = "入力パラメータ数(%d)エラー";
	public final String CP5J110006 = "入力パラメータ長(%s:%d)エラー";

	// ****************共通検索一覧表示用情報作成用****************
	public final String CP5J110001 = "共通検索一覧表示用情報作成プログラムを開始します。";
	public final String CP5J110002 = "共通検索一覧表示用情報作成ジョブが正常終了しました。";
	public final String CP5J110003 = "共通検索一覧表示用情報作成ジョブが警告終了しました。";
	public final String CP5J110004 = "共通検索一覧表示用情報作成ジョブが異常終了しました。";
	public final String CP5J110007 = "処理区分(%s)エラー";
	public final String CP5J110008 = "データ作成定義ファイル(%s)読み込みエラー";
	public final String CP5J110010 = "JPP表示用情報(%s)読み込みエラー";
	public final String CP5J110011 = "入力レコード長(%d行目:%d)エラー";
	public final String CP5J110012 = "必須項目(%d行目:%d列目)チェックエラー";
	public final String CP5J110013 = "任意項目(%d行目:%d列目)MAXサイズチェックエラー";
	public final String CP5J110014 = "必須項目(%d行目:%d列目)MAXサイズチェックエラー";
	public final String CP5J110015 = "任意項目(%d行目:%d列目)半角数字チェックエラー";
	public final String CP5J110016 = "必須項目(%d行目:%d列目)半角数字チェックエラー";
	public final String CP5J110017 = "任意項目(%d行目:%d列目)半角英数字チェックエラー";
	public final String CP5J110018 = "必須項目(%d行目:%d列目)半角英数字チェックエラー";
	public final String CP5J110019 = "繰り返し数(%d行目:%d列目:%d)MAX数チェックエラー";
	public final String CP5J110022 = "共通検索一覧表示用情報出力エラー(%d行目)";
	public final String CP5J110023 = "番号索引用情報データ出力エラー(%d行目)";
	public final String CP5J110024 = "共通検索一覧表示用情報エラー一覧出力エラー(%d行目)";
	public final String CP5J110025 = "レコード(%d行目)の処理が正常に行われました。";
	public final String CP5J110026 = "レコード(%d行目)の処理で警告が発生しました。";
	public final String CP5J110027 = "レコード(%d行目)の処理でデータエラーが発生しました。";

	// ****************共通検索一覧表示用情報登録用****************
	public final String  CP5J200002 = "共通検索一覧表示用情報登録ジョブが正常終了しました。";
	public final String  CP5J200004 = "共通検索一覧表示用情報登録ジョブが異常終了しました。";
	public final String  CP5J210001 = "共通検索一覧表示用情報登録プログラムを開始します。";
	public final String  CP5J210002 = "共通検索一覧表示用情報登録プログラムが正常終了しました。";
	public final String  CP5J210004 = "共通検索一覧表示用情報登録プログラムが異常終了しました。";
	public final String  CP5J210010 = "最大レコードID取得に失敗しました。(SQLCODE=%d)";
	public final String  CP5J210011 = "共通検索一覧表示用情報(%s)読み込みエラー";
	public final String  CP5J210014 = "共通検索一覧表示用情報管理テーブル(%s)へレコードを追加しました。(ISN=%d)";
	public final String  CP5J210015 = "共通検索一覧表示用情報管理テーブル(%s)へのレコード追加に失敗しました。(ISN=%d)(SQLCODE=%d)";
	public final String  CP5J210016 = "共通検索一覧表示用情報管理テーブル(%s)のレコードを削除しました。(ISN=%d)";
	public final String  CP5J210019 = "共通検索一覧表示用情報管理テーブル(%s)の重複レコードチェックに失敗しました。(ISN=%d)(SQLCODE=%d)";
	public final String  CP5J210020 = "蓄積確認パラメータ出力エラー(%d行目)";
	public final String  CP5J210021 = "レコード(%d行目)の処理が正常に行われました。";
	public final String  CP5J210022 = "レコード(%d行目)の処理でデータエラーが発生しました。";
	public final String  CP5J210023 = "DBコミットを実施しました。(%d行目)(コミット件数合計=%d)";
	public final String  CP5J210024 = "DBコミットに失敗しました。(%d行目)(SQLCODE=%d)";
	public final String  CP5J210025 = "DBロールバックを実施しました。";
	public final String  CP5J210026 = "DBロールバックに失敗しました。(SQLCODE=%d)";
	public final String  CP5J210029 = "共通検索一覧表示用情報管理テーブル(%s)のレコード削除に失敗しました。(ISN=%d)(SQLCODE=%d)";
	public final String  CP5J210030 = "処理対象DB(%s)エラー";
	public final String  CP5J210031 = "共通検索一覧表示用情報管理テーブル(%s)の重複レコードが2件以上存在します。(ISN=%d)(件数=%d)";
	public final String  CP5J210041 = "共通検索一覧表示用情報管理テーブル(%s)の蓄積確認件数取得に失敗しました。(SCD_KY=%s)(SQLCODE=%d)";
	public final String  CP5J210042 = "共通検索一覧表示用情報管理テーブル(%s)の蓄積確認上位10件取得に失敗しました。(SCD_KY=%s)(SQLCODE=%d)";
	public final String  CP5J210043 = "共通検索一覧表示用情報管理テーブル(%s)の蓄積確認下位5件(降順)取得に失敗しました。(SCD_KY=%s)(SQLCODE=%d)";

	// ****************共通検索一覧表示用情報文蓄情報ファイル作成用****************
	public final String CP5J510001 = "共通検索一覧表示用情報文蓄情報ファイル作成プログラムを開始します。";
	public final String CP5J510002 = "共通検索一覧表示用情報ファイル作成プログラムが正常終了しました。";
	public final String CP5J510004 = "共通検索一覧表示用情報ファイル作成プログラムが異常終了しました。";
	public final String CP5J510009 = "共通検索一覧表示用情報管理テーブル(%s)からの文蓄情報取得に失敗しました。(SQLCODE=%d)";
	public final String CP5J510011 = "共通検索一覧表示用情報文蓄情報一時ファイル出力エラー(%d行目)";
	public final String CP5J510016 = "文蓄情報作成定義取得エラー(%s)";
}
