import apiClient from "../api";

export async function getFolderTree() {
    const response = await apiClient.get("/folders/tree");
    return response.data;
}
