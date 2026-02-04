import type { CourseResponse } from "./CourseResponce";

/**
 * 受講生詳細／一覧取得APIから返却されるレスポンスの型。
 *
 * バックエンドで保持している受講生情報と、
 * 受講生に紐づくコース情報一覧をまとめた構造を表します。
 */
export type StudentResponse = {
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
  courseList: CourseResponse[];
};
