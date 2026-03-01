import type { StudentResponse } from "../types/StudentResponse";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import type { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";

/**
 * API のベースURLです。
 * Vite の環境変数（.env）から取得します。
 */
const BASE_URL = import.meta.env.VITE_API_URL;

/**
 * 受講生一覧の条件検索で使用するクエリパラメータの型です。
 * 未指定の項目はクエリに含まれません。
 */
type FilterParams = {
  studentId?: string;
  studentFullName?: string;
  studentFurigana?: string;
  studentNickname?: string;
  email?: string;
  prefecture?: string;
  city?: string;
  ageFrom?: number;
  ageTo?: number;
  gender?: string;
  studentRemark?: string;
  studentIsDeleted?: boolean;
  courseName?: string;
  status?: string;
};

/**
 * 条件を指定して受講生一覧を取得します。
 *
 * 指定された検索条件をクエリパラメータに変換し、
 * 受講生一覧取得APIを呼び出します。
 *
 * @param params 検索条件（未指定の項目は無視される）
 * @returns 条件に一致した受講生一覧
 */
export const filterStudentApi = async (
  params: FilterParams,
): Promise<StudentResponse[]> => {
  const queryParams = new URLSearchParams();

  if (params.studentId) queryParams.append("studentId", params.studentId);
  if (params.studentFullName)
    queryParams.append("studentFullName", params.studentFullName);
  if (params.studentFurigana)
    queryParams.append("studentFurigana", params.studentFurigana);
  if (params.studentNickname)
    queryParams.append("studentNickname", params.studentNickname);
  if (params.email) queryParams.append("email", params.email);
  if (params.prefecture) queryParams.append("prefecture", params.prefecture);
  if (params.city) queryParams.append("city", params.city);
  if (params.ageFrom !== undefined)
    queryParams.append("ageFrom", String(params.ageFrom));
  if (params.ageTo !== undefined)
    queryParams.append("ageTo", String(params.ageTo));
  if (params.gender) queryParams.append("gender", params.gender);
  if (params.studentRemark)
    queryParams.append("studentRemark", params.studentRemark);
  if (params.studentIsDeleted !== undefined)
    queryParams.append("studentIsDeleted", String(params.studentIsDeleted));
  if (params.courseName) queryParams.append("courseName", params.courseName);
  if (params.status) queryParams.append("status", params.status);

  const res = await fetch(`${BASE_URL}/students?${queryParams.toString()}`);
  if (!res.ok) throw new Error("条件検索に失敗しました");

  return await res.json();
};

/**
 * バリデーションエラーの詳細情報を表す型です。
 */
type ValidationError = {
  field: string;
  message: string;
};

/**
 * バリデーションエラー発生時のAPIレスポンス型です。
 */
type ErrorResponse = {
  error: string;
  details: ValidationError[];
};

/**
 * 新規受講生を登録します。
 *
 * フォームで入力された受講生情報をもとに、
 * 新規受講生登録APIを呼び出します。
 *
 * @param payload 新規受講生登録用のリクエストボディ
 */
export const registerStudentApi = async (
  payload: NewStudentFormValues,
): Promise<void> => {
  const res = await fetch(`${BASE_URL}/students`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    let errMessage = "登録に失敗しました";

    try {
      const err: string | ErrorResponse = await res.json();

      if (typeof err === "string") {
        // メッセージのみ返却される場合
        errMessage = err;
      } else if (err && typeof err === "object" && "error" in err) {
        // バリデーションエラーの場合
        const messages = (err.details ?? [])
          .map((d: ValidationError) => `${d.field}: ${d.message}`)
          .join("\n");
        errMessage = `${err.error}\n${messages}`;
      }
    } catch {
      // JSON 解析に失敗した場合（空レスポンスなど）
      errMessage = "登録に失敗しました";
    }

    throw new Error(errMessage);
  }
};

/**
 * 既存の受講生情報を更新します。
 *
 * 指定された受講生IDに対して、
 * フォームで入力された受講生情報をもとに更新APIを呼び出します。
 *
 * @param studentId 更新対象の受講生ID
 * @param payload 受講生更新用のリクエストボディ
 */
export const updateStudentApi = async (
  studentId: string,
  payload: UpdateStudentFormValues,
): Promise<void> => {
  const res = await fetch(`${BASE_URL}/students/${studentId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.message || "更新に失敗しました");
  }
};
