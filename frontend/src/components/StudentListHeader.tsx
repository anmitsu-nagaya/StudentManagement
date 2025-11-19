import * as FaIcons from "react-icons/fa";
import { useNavigate } from "react-router-dom";

type HeaderProps = {
  activeTab: "students" | "courses";
  onTabChange: (tab: "students" | "courses") => void;
};

const styles = {
  headerContainer: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "16px 24px",
    backgroundColor: "#f5f5f5",
    borderBottom: "1px solid #ddd",
  },
  tabs: {
    display: "flex",
    gap: "8px",
  },
  tab: {
    padding: "8px 20px",
    border: "1px solid #ccc",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    transition: "all 0.2s",
  },
  tabActive: {
    padding: "8px 20px",
    border: "1px solid #2196f3",
    backgroundColor: "#2196f3",
    color: "white",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    transition: "all 0.2s",
  },
  headerActions: {
    display: "flex",
    gap: "12px",
  },
  iconButton: {
    padding: "8px 12px",
    border: "1px solid #ccc",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "16px",
    transition: "all 0.2s",
  },
};

export const Header = (props: HeaderProps) => {
  const { activeTab, onTabChange } = props;
  const navigate = useNavigate();

  const handleRegisterClick = () => {
    console.log("新規登録ボタンがクリックされました");
    console.log("遷移先: /students/register");
    navigate("/students/register");
  };

  const handleFilterClick = () => {
    console.log("検索ボタンがクリックされました");
    console.log("遷移先: /students/filter");
    navigate("/students/filter");
  };

  return (
    <div style={styles.headerContainer}>
      <div style={styles.tabs}>
        <button
          style={activeTab === "students" ? styles.tabActive : styles.tab}
          onClick={() => onTabChange("students")}
        >
          受講生一覧
        </button>
        <button
          style={activeTab === "courses" ? styles.tabActive : styles.tab}
          onClick={() => onTabChange("courses")}
        >
          コース一覧
        </button>
      </div>
      <div style={styles.headerActions}>
        <button style={styles.iconButton} onClick={handleRegisterClick}>
          <FaIcons.FaPlus />
        </button>
        <button style={styles.iconButton} onClick={handleFilterClick}>
          <FaIcons.FaFilter />
        </button>
      </div>
    </div>
  );
};
