import type { UpdateSCourseFormValues } from "./UpdateCourseFormValues";

/**
 * 受講生情報更新時に使用するフォーム入力値の型。
 *
 * 既存の受講生データを編集し、
 * フロントエンドから受講生更新APIへ送信する payload 構造を表します。
 * 受講生の基本情報と、受講中コースのステータス更新情報を含みます。
 */
export type UpdateStudentFormValues = {
  student: {
    studentId: string;
    studentFullName: string;
    studentFurigana: string;
    studentNickname: string | null;
    email: string;
    prefecture: string | null;
    city: string | null;
    age: number | null;
    gender: string | null;
    studentRemark: string | null;
    studentIsDeleted: boolean | null;
  };
  courseList: UpdateSCourseFormValues[];
};
