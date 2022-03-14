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

export type SubscribedPodcast = Pick<Podcast, "id" | "description" | "imageUrl" | "name">

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

export interface User {
    id: number;
    name: string;
    password: string;
    preferences: any; //TODO separate interface
    subscriptions: Podcast[];
}
