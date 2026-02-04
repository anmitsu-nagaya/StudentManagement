import type { CourseStatus } from "./CourseStatus";

/**
 * 受講生のコース情報と申込状況をまとめた API レスポンス型。
 * コース基本情報（course）と、申込・受講状況を表すステータス情報（status）を含む。
 */
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
