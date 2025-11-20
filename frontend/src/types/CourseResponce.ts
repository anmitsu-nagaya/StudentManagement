import { CourseStatus } from "./CourseStatus";

export type CourseResponse = {
  courseId: number;
  studentId: string;
  courseName: string;
  status: CourseStatus;
  temporaryAppliedAt: string;
  officialAppliedAt: string | null;
  courseStartedAt: string | null;
  courseCompletedAt: string | null;
};
