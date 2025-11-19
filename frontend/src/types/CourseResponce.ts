import type { CourseStatus } from "./CourseStatus";

export type CourseResponse = {
  course: {
    courseId: number;
    studentId: string;
    courseName: string;
  };
  status: {
    statusId: number;
    courseId: number;
    status: CourseStatus;
    temporaryAppliedAt: string;
    officialAppliedAt: string | null;
    courseStartedAt: string | null;
    courseCompletedAt: string | null;
  };
};
