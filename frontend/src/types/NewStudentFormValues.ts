import { NewCourseFormValues } from "./NewCourseFormValues";

export type NewStudentFormValues = {
  studentFullName: string;
  studentFurigana: string;
  studentNickname?: string;
  email: string;
  prefecture?: string;
  city?: string;
  age?: number;
  gender?: string;
  studentRemark?: string;
  courseList: NewCourseFormValues[];
};
