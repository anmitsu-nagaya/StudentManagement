import type { UpdateSCourseFormValues } from "./UpdateCourseFormValues";

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
