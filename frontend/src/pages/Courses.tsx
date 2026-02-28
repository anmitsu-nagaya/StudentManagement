import { useNavigate } from "react-router-dom";

export const Courses = () => {
  const navigate = useNavigate();

  return (
    <div style={{ padding: "40px", textAlign: "center" }}>
      <h1 style={{ marginBottom: "16px" }}>コース一覧</h1>
      <p style={{ color: "#666", marginBottom: "24px" }}>実装予定</p>
      <button
        onClick={() => navigate("/students")}
        style={{
          padding: "10px 24px",
          backgroundColor: "#2196f3",
          color: "#fff",
          border: "none",
          borderRadius: "4px",
          cursor: "pointer",
        }}
      >
        ← 受講生一覧へ戻る
      </button>
    </div>
  );
};
