export interface Podcast {
    id: number;
    author: string;
    description: string;
    episodes: Episode[];
    imageUrl: string;
    lastBuildDate: string;
    link: string;
    name: string;
    rssFeed: string;
}

export type SubscribedPodcast = Pick<Podcast, "id" | "description" | "imageUrl" | "name">;

export interface Episode {
    id: number;
    creator: string;
    description: string;
    downloaded: boolean;
    fileLength: number;
    fileLocation: string;
    fileName: string;
    filePath: string;
    fileType: string;
    fileUrl: string;
    guid: string;
    imageUrl: string;
    keywords: string;
    link: string;
    podcaseUrl: string;
    publicationDate: Date;
    retrievedDate: Date;
    subtitle: string;
    summary: string;
    title: string;
    playLength?: number;
}

export interface SubscribedEpisode {
    id: number;
    creator: string;
    description: string;
    downloaded: boolean;
    file_length: number;
    file_location: string;
    file_name: string;
    file_path: string;
    file_type: string;
    file_url: string;
    guid: string;
    image_url: string;
    keywords: string;
    link: string;
    podcase_url: string;
    publication_date: Date;
    retrieved_date: Date;
    subtitle: string;
    summary: string;
    title: string;
    play_length: number;
    duration: number;
    play_state_id: number;
}

export interface PlayState {
    userId: number,
    episodeId: number,
    playLength: number,
    lastPlayed: number,
    id: number | null,
}

export interface User {
    id: number;
    name: string;
    password: string;
    preferences: any; //TODO separate interface
    subscriptions: Podcast[];
    imageUrl: string;
}

export enum GridRoutes {
    PODCAST_ALL,
    PODCAST_SUBSCRIPTION,
    PODCAST_FAVOURITE,
    SEARCH
}

export interface NavigationState {
    podcastState: GridRoutes;
}

export interface AppState {
    currentUser: User | null;
    currentEpisode: SubscribedEpisode | null;
    headerText: string;
}

export enum ActionType {
    ChangeUser,
    ChangeEpisode,
    ChangeHeaderText
}

export interface ChangeUser {
    type: ActionType.ChangeUser;
    payload: User;
}

export interface ChangeEpisode {
    type: ActionType.ChangeEpisode;
    payload: SubscribedEpisode;
}

export interface ChangeHeaderText {
    type: ActionType.ChangeHeaderText;
    payload: string;
}

export type StateActions = ChangeUser | ChangeEpisode | ChangeHeaderText;

export const initialAppState: AppState = {
    currentUser: null,
    currentEpisode: null,
    headerText: "",
};
