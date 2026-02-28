import { useNavigate } from "react-router-dom";

export const Login = () => {
  const navigate = useNavigate();

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "100vh",
        gap: "24px",
      }}
    >
      <h1 style={{ fontSize: "28px" }}>受講生管理システム</h1>
      <p style={{ color: "#666" }}>※ログイン機能は実装予定</p>
      <button
        onClick={() => navigate("/students")}
        style={{
          padding: "12px 32px",
          backgroundColor: "#2196f3",
          color: "#fff",
          border: "none",
          borderRadius: "4px",
          fontSize: "16px",
          cursor: "pointer",
        }}
      >
        ログイン
      </button>
    </div>
  );
};
