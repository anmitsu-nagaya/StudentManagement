import { StudentResponce } from "../types/StudentResponce";
import { NewStudentFormValues } from "../types/NewStudentFormValues";
import { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";

const BASE_URL = "http://localhost:8080";

// 一覧取得
export const getStudentList = async (): Promise<StudentResponce[]> => {
  const res = await fetch(`${BASE_URL}/students`);
  if (!res.ok) throw new Error("一覧取得に失敗しました");
  return await res.json();
};

// 条件検索
export const getFilterStudentList = async (
  params: Record<string, any>
): Promise<StudentResponce[]> => {
  const query = new URLSearchParams(params).toString();
  const res = await fetch(`${BASE_URL}/students/filter?${query}`);
  if (!res.ok) throw new Error("条件検索に失敗しました");
  return await res.json();
};

// 新規登録
export const registerStudent = async (
  payload: NewStudentFormValues
): Promise<void> => {
  const res = await fetch(`${BASE_URL}/register-student`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    let errMessage = "登録に失敗しました";

    try {
      const err = await res.json(); // JSON 形式のレスポンスを取得

      if (typeof err === "string") {
        errMessage = err; // HttpMessageNotReadableException の場合
      } else if (err && typeof err === "object" && "error" in err) {
        // バリデーションエラーの場合
        const messages = (err.details ?? [])
          .map((d: any) => `${d.field}: ${d.message}`)
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
