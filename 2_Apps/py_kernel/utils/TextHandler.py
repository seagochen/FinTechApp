

def trim_tailer_from_text(text: str):
    """
    Windows and Linux systems have different line endings.
    These special symbols may cause regular matching to fail, so need to remove them all
    """
    if text[-2] == '\r':
        text = text.replace("\r\n", "")
    else:
        text = text.replace("\n", "")

    return text