import type { CourseStatus } from "./CourseStatus";

export type UpdateSCourseFormValues = {
  status: {
    statusId: number;
    courseId: number;
    status: CourseStatus;
  };
};
