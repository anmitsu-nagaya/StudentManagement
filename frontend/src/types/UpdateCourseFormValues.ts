import type { CourseStatus } from "./CourseStatus";

/**
 * 受講生に紐づくコースの受講ステータス更新時に使用するフォーム入力値の型。
 *
 * 画面で選択・変更された受講ステータスをもとに、
 * フロントエンドからステータス更新APIへ送信する payload 構造を表します。
 */
export type UpdateSCourseFormValues = {
  status: {
    statusId: number;
    courseId: number;
    status: CourseStatus;
  };
};
