/**
 * 新規コース登録時に使用するフォーム入力値の型。
 * フロントエンドから API に送信する payload 構造を表す。
 */
export type NewCourseFormValues = {
  course: {
    courseName: string;
  };
};
