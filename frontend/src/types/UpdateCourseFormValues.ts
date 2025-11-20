import { CourseStatus } from "./CourseStatus";

export type UpdateSCourseFormValues = {
  statusId: number;
  courseId: number;
  status: CourseStatus;
};
