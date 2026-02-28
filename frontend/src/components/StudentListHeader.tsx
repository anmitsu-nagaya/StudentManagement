import * as FaIcons from "react-icons/fa";
import { useNavigate } from "react-router-dom";

type HeaderProps = {
  /**
   * 受講生テーブルとコーステーブルのうち、表示中のテーブルを示しているタブを管理する
   */
  activeTab: "students" | "courses";

  /**
   * 登録ボタンの押下によりモーダルを開くためのコールバック関数
   */
  onRegisterClick: () => void;
  /**
   * フィルターボタンの押下によりモーダルを開くためのコールバック関数
   */
  onFilterClick: () => void;
  /**
   * フィルター解除ボタンの押下によりフィルターを解除するためのコールバック関数
   */
  onClearFilterClick: () => void;
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
  leftSection: {
    display: "flex",
    alignItems: "center",
    gap: "24px",
  },
  title: {
    fontSize: "20px",
    fontWeight: "bold",
    color: "#333",
    margin: 0,
    whiteSpace: "nowrap" as const,
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
    marginLeft: "auto",
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
  filterGroup: {
    display: "flex",
    border: "1px solid #ccc",
    borderRadius: "4px",
    overflow: "hidden",
    backgroundColor: "#fff",
  },
  filterButton: {
    padding: "8px 12px",
    border: "none",
    borderRight: "1px solid #ccc",
    backgroundColor: "#fff",
    cursor: "pointer",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "16px",
    transition: "all 0.2s",
  },
  clearFilterButton: {
    padding: "8px 12px",
    border: "none",
    backgroundColor: "#fff",
    cursor: "pointer",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "16px",
    transition: "all 0.2s",
  },
};

/**
 * ヘッダーを管理するコンポーネント
 */
export const Header = (props: HeaderProps) => {
  const { activeTab, onRegisterClick, onFilterClick, onClearFilterClick } =
    props;
  const navigate = useNavigate();
  return (
    <div style={styles.headerContainer}>
      <div style={styles.leftSection}>
        <h1 style={styles.title}>受講生管理システム</h1>
        <div style={styles.tabs}>
          <button
            style={activeTab === "students" ? styles.tabActive : styles.tab}
            onClick={() => navigate("/students")}
          >
            受講生一覧
          </button>
          <button
            style={activeTab === "courses" ? styles.tabActive : styles.tab}
            onClick={() => navigate("/courses")}
          >
            コース一覧
          </button>
        </div>
      </div>

      {/* ← ここに移動 */}
      <div style={styles.headerActions}>
        <button style={styles.iconButton} onClick={onRegisterClick}>
          <FaIcons.FaPlus />
        </button>
        <div style={styles.filterGroup}>
          <button
            style={styles.filterButton}
            onClick={onFilterClick}
            title="検索"
          >
            <FaIcons.FaFilter />
          </button>
          <button
            style={styles.clearFilterButton}
            onClick={onClearFilterClick}
            title="フィルター解除"
          >
            <FaIcons.FaTimesCircle />
          </button>
        </div>
      </div>
    </div>
  );
};
