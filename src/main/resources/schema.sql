CREATE TABLE IF NOT EXISTS spring_ai_chat_memory (
                                                     id BIGSERIAL PRIMARY KEY,
                                                     conversation_id TEXT NOT NULL,
                                                     content TEXT NOT NULL,
                                                     type VARCHAR(200) NOT NULL,
    "timestamp" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_sacm_conversation_id ON spring_ai_chat_memory(conversation_id);