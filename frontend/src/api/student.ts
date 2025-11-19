import type { StudentResponse } from "../types/StudentResponce";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import type { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";

const BASE_URL = "http://localhost:8080";

// 一覧取得
export const getStudentList = async (): Promise<StudentResponse[]> => {
  const res = await fetch(`${BASE_URL}/students`);
  if (!res.ok) throw new Error("一覧取得に失敗しました");
  return await res.json();
};

// 条件検索
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

export const getFilterStudentList = async (
  params: FilterParams
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

  const res = await fetch(
    `${BASE_URL}/students/filter?${queryParams.toString()}`
  );
  if (!res.ok) throw new Error("条件検索に失敗しました");
  return await res.json();
};

// 新規登録
type ValidationError = {
  field: string;
  message: string;
};

type ErrorResponse = {
  error: string;
  details: ValidationError[];
};

export const registerStudent = async (
  payload: NewStudentFormValues
): Promise<void> => {
  console.log(
    "registerStudent関数に渡されたpayload:",
    JSON.stringify(payload, null, 2)
  ); // ← 追加

  const res = await fetch(`${BASE_URL}/register-student`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  console.log("送信したJSON:", JSON.stringify(payload)); // ← 追加
  console.log("レスポンスステータス:", res.status); // ← 追加

  if (!res.ok) {
    let errMessage = "登録に失敗しました";

    try {
      const err: string | ErrorResponse = await res.json();

      if (typeof err === "string") {
        errMessage = err; // HttpMessageNotReadableException の場合
      } else if (err && typeof err === "object" && "error" in err) {
        // バリデーションエラーの場合
        const messages = (err.details ?? [])
          .map((d: ValidationError) => `${d.field}: ${d.message}`)
          .join("\n");
        errMessage = `${err.error}\n${messages}`;
      }
    } catch {
      // res.json() が例外になった場合（空レスポンスなど）
      errMessage = "登録に失敗しました";
    }

    throw new Error(errMessage);
  }
};

// 更新
export const updateStudent = async (
  payload: UpdateStudentFormValues
): Promise<void> => {
  const res = await fetch(`${BASE_URL}/update-student`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.message || "更新に失敗しました");
  }
};
