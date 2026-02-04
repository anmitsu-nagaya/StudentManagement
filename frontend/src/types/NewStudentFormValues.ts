import type { NewCourseFormValues } from "./NewCourseFormValues";

/**
 * 新規受講生登録時に使用するフォーム入力値の型。
 *
 * 画面で入力された内容をもとに、
 * フロントエンドから新規受講生登録APIへ送信する payload 構造を表します。
 */
export type NewStudentFormValues = {
  student: {
    studentFullName: string;
    studentFurigana: string;
    studentNickname?: string;
    email: string;
    prefecture?: string;
    city?: string;
    age?: number;
    gender?: string;
    studentRemark?: string;
  };
  courseList: NewCourseFormValues[];
};
