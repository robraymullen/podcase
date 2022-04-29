import { DeadDownload } from "../../Types";

interface DeadItemProps {
    children: never[];
    key: string;
    download: DeadDownload;
}

const DeadDownloadListItem = ({download}: DeadItemProps) => {
    return (
        <div>

        </div>
    )
};

export default DeadDownloadListItem;